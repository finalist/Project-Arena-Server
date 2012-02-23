package nl.kennisnet.arena.services;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.services.factories.DTOFactory;
import nl.kennisnet.arena.services.factories.DomainObjectFactory;
import nl.kennisnet.arena.services.support.HibernateAwareService;
import nl.kennisnet.arena.utils.GamarayDataBean;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.RemoveTag;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
@Transactional
public class QuestService extends HibernateAwareService {

	private static final int INITIAL_SCORE = 0;
	private static final String newLine = System.getProperty("line.separator");

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private CompositeConfiguration configuration;

	@Autowired
	private ParticipantService participantService;

	private Logger log = Logger.getLogger(QuestService.class);

	public Quest getQuest(final Long questId) {
		return get(Quest.class, questId);
	}

	public void getQuest(final Long questId,
			final TransactionalCallback<Quest> callback) {
		callback.onResult(getQuest(questId));
	}

	public QuestDTO getQuestDTO(Long id) {
		Quest quest = get(Quest.class, id);
		if (quest != null) {
			return DTOFactory.create(quest);
		}
		return null;
	}

	public void buildQrCodesPdf(String url, long questId,
			OutputStream outputStream) {
		QrCodesPdfBuilder builder = new QrCodesPdfBuilder(url,
				participantService.getAllParticipants());
		builder.setQuestId(questId);
		builder.build(outputStream);
	}

	public long participateInQuest(long participantId, Quest quest) {

		Participant participant = get(Participant.class, participantId);

		Criteria criteria = getSession().createCriteria(Participation.class);
		criteria.add(Restrictions.eq("participant", participant));
		criteria.add(Restrictions.eq("quest", quest));
		criteria.setMaxResults(1);

		List<Participation> participations = criteria.list();
		if (participations.size() > 0) {
			return participations.get(0).getId();
		} else {
			Participation participation = new Participation(participant, quest,
					INITIAL_SCORE);
			return merge(participation).getId();
		}
	}

	public QuestDTO save(QuestDTO questDTO) {
		Quest originalQuest = null;
		if (questDTO.getId() != null) {
			originalQuest = get(Quest.class, questDTO.getId());
		}
		Quest quest = null;
		List<Positionable> deletingPos = new ArrayList<Positionable>();
		if (originalQuest == null) {
			quest = DomainObjectFactory.create(questDTO);
		} else {
			quest = DomainObjectFactory.update(questDTO, originalQuest);
			deletingPos = DomainObjectFactory.delete(quest, originalQuest);
			delete(deletingPos);
		}

		if (quest.getId() != null) {
			if (!savedByOwner(quest, originalQuest)) {
				quest.setId(null);
			}
		}

		quest = merge(quest);
		sendNotification(quest);
		return DTOFactory.create(quest);
	}

	private boolean savedByOwner(Quest quest, Quest originalQuest) {
		return originalQuest != null
				&& originalQuest.getEmailOwner().equals(quest.getEmailOwner());
	}

	private void sendNotification(Quest quest) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(quest.getEmailOwner());
			helper.setText(createMailText(quest));
			helper.setSubject("Bewaard: ARena " + quest.getName());
			helper.setFrom(configuration.getString("mail.from.adres"));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			System.out.println(createGamarayUrl(quest));
			buildQrCodesPdf(createGamarayUrl(quest), quest.getId(),
					outputStream);
			byte[] byteArray = outputStream.toByteArray();
			InputStreamSource source = new ByteArrayResource(byteArray);
			helper.addAttachment(String.format("ToegangsTickets_%s_%s.pdf",
					quest.getName(), currentShortDate(new Locale("nl"))),
					source);
			mailSender.send(message);
		} catch (MessagingException ex) {
			log.error("Failed to send saved quest mail", ex);
		}

	}

	private String createGamarayUrl(Quest quest) {
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		String url = String.format("%smixare/%d.mix", baseUrl, quest.getId());
		return url;
	}

	private String createMailText(Quest quest) {
		StringBuffer result = new StringBuffer();
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		String designerUrl = String.format("%smain.do?arenaId=%s", baseUrl,
				quest.getId());
		// String monitorUrl = String.format("%s/ARenaMonitor.html?arenaId=%s",
		// baseUrl, quest.getId());

		result.append("Beste " + quest.getEmailOwner() + newLine + newLine);
		result.append("U heeft op " + currentDate(new Locale("nl")) + " om "
				+ currentTime(new Locale("nl")) + " uw ontwerp opgeslagen:"
				+ newLine);
		result.append("Arena : " + quest.getName() + " " + newLine);

		result.append("Opgeslagen op: " + designerUrl + "" + newLine);
		result.append("" + newLine);
		result.append("U kunt uw ontwerp op ieder moment aanpassen via de bovenstaande link. "
				+ newLine);
		result.append("" + newLine);
		result.append("Gebruik maken van de Arena" + newLine);

		result.append("ARena " + quest.getName()
				+ " is nu actief en deelnemers kunnen hem 24 uur/dag ervaren. "
				+ newLine);

		result.append("Om de deelnemers de Arena te laten ervaren dienen zij in het bezit te zijn van een toegangsticket. "
				+ "Door de toegangstickets (PDF) uit te printen en aan de deelnemers verstrekken hebben zij toegang tot de Arena."
				+ newLine);
		result.append("" + newLine);

		result.append("Resultaten van de deelnemers" + newLine);

		result.append("Via onderstaande link vindt u direct de resultaten van de deelnemende teams."
				+ " Het overzicht wordt Live ververst, bij iedere activiteit in uw ARena."
				+ newLine);
		result.append(newLine);

		result.append("Het PDF bestand kan niet worden geopend" + newLine);
		result.append("Het PDF bestand bevat de elektronische toegangstickets. Om dit bestand te openen heeft u de software “Adobe Reader” nodig. "
				+ "Installeer de gratis software Adobe Reader (http://get.adobe.com/reader/)  en open het bestand nogmaals "
				+ newLine);
		result.append("" + newLine);
		result.append("" + newLine);

		result.append("Veel leerplezier," + newLine);
		result.append("" + newLine);

		result.append("het ARena Team" + newLine);
		result.append("E: arena@kennisnet.nl" + newLine);
		result.append("" + newLine);

		result.append("Stichting Kennisnet" + newLine);
		result.append("Surfnet/Kennisnet Innovatieprogramma" + newLine);
		result.append("" + newLine);

		result.append("ARena is een experimenteel project. Wij ontvangen graag uw verbetersuggesties, ervaringen en vragen."
				+ newLine);

		return result.toString();
	}

	private String currentDate(Locale locale) {
		SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy",
				locale);
		return format.format(new Date());
	}

	private String currentShortDate(Locale locale) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy", locale);
		return format.format(new Date());
	}

	private String currentTime(Locale locale) {
		SimpleDateFormat format = new SimpleDateFormat("k:mm", locale);
		return format.format(new Date());
	}

	public void handlePress(Quest quest, GamarayDataBean data) {
		if (data.getEventSrc().startsWith("feature")) {
			handleFeaturePress();
		} else if (data.getEventSrc().startsWith("feature")) {
			handleOverlayPress();
		} else {
			throw new NotImplementedException();
		}
	}

	private void handleFeaturePress() {
		throw new NotImplementedException();
	}

	private void handleOverlayPress() {
		throw new NotImplementedException();
	}
}
