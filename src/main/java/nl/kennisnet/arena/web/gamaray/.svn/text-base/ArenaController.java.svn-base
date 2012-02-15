package nl.kennisnet.arena.web.gamaray;

import java.util.List;
import java.util.Map;

import javax.transaction.NotSupportedException;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.formats.Dimension;
import nl.kennisnet.arena.formats.convert.DimensionFactory;
import nl.kennisnet.arena.model.Progress;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.services.QuestService;
import nl.kennisnet.arena.services.TransactionalCallback;
import nl.kennisnet.arena.services.factories.GeomUtil;
import nl.kennisnet.arena.utils.GamarayDataBean;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vividsolutions.jts.geom.Point;

@Controller
@RequestMapping("/gamaray")
public class ArenaController {

	private final QuestService questService;
	private final ParticipantService participantService;
	private final CompositeConfiguration configuration;

	private Logger log = Logger.getLogger(ArenaController.class);

	@Autowired
	public ArenaController(final QuestService questService, final ParticipantService participantService, final CompositeConfiguration configuration) {
		this.questService = questService;
		this.participantService = participantService;
		this.configuration = configuration;
	}

	/**
	 * The browser does a GET to this url and the content-type is set to
	 * application/gamaray-gddf. This starts gamaray. Gamaray then does a POST
	 * to the exact same URL.
	 * 
	 * @param questName
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{questId}", method = RequestMethod.GET, params = { "player" })
	public ModelAndView gamarayCallback(@PathVariable Long questId, @RequestParam("player") final String player) {

		final ModelAndView modelAndView = new ModelAndView("gamaray");
		final GamarayDataBean data = new GamarayDataBean();
		data.setPlayer(player);

		log.debug("default get: [quest=" + questId + ",player=" + player + "]");

		questService.getQuest(questId, new TransactionalCallback<Quest>() {

			@Override
			public void onResult(Quest result) {
				final Dimension dimension = DimensionFactory.getEmptyInstance(result, data, configuration);
				modelAndView.addObject(dimension);
			}
		});

		log.debug("response model: " + modelAndView.getModel());

		return modelAndView;
	}

	/**
	 * Gamaray POSTs important data of its current state, which we can base
	 * decisions about the new game state on.
	 * 
	 * @param questId
	 * @param data
	 * @return
	 * @throws NotSupportedException
	 */
	@RequestMapping(value = "/{questId}", method = RequestMethod.POST)
	public ModelAndView gamarayCallback(@PathVariable Long questId, @ModelAttribute("data") GamarayDataBean data) throws NotSupportedException {

		ModelAndView modelAndView = new ModelAndView("gamaray");
		data.setQuestId(questId);

		log.debug("request: " + data);

		if ("init".equals(data.getEvent()) && !StringUtils.hasText(data.getAnswer())) {
			modelAndView = onInitEvent(data);
		} else if ("refreshOnTime".equals(data.getEvent()) || "refreshOnDistance".equals(data.getEvent())) {
			modelAndView = onRefreshEvent(data);
		} else if ("refreshOnPress".equals(data.getEvent()) || ("init".equals(data.getEvent()) && StringUtils.hasText(data.getAnswer()))) {
			data.setEvent("refreshOnPress");
			modelAndView = onPressEvent(data);
		} else {
			// Not a valid event type, return exception
	      log.error("Event type not supported : " + data);
			throw new NotSupportedException("Event type not supported");
		}

		log.debug("response model: " + modelAndView.getModel());

		return modelAndView;
	}

	private ModelAndView onInitEvent(final GamarayDataBean data) {
		final ModelAndView modelAndView = new ModelAndView("gamaray");

		final long participantId = participantService.getParticipant(data.getPlayer());
		final Quest quest = questService.getQuest(data.getQuestId());
		final long participationId = questService.participateInQuest(participantId, quest);

		participantService.addParticipationLog(participationId, data.getTime(), data.getEvent(), data.getLocation());
		
		final Map<MultiKey,Integer> answers=participantService.getAnswers(quest.getId());
		final Progress progress = participantService.getProgress(participationId);
		
		questService.getQuest(data.getQuestId(), new TransactionalCallback<Quest>() {

			@Override
			public void onResult(Quest result) {
				final Dimension dimension = DimensionFactory.getInstance(result, data, configuration, progress, answers,participantId);
		      log.error("Response on init : " + dimension);

				modelAndView.addObject(dimension);
			}
		});

		return modelAndView;
	}

	private ModelAndView onRefreshEvent(final GamarayDataBean data) {
		final ModelAndView modelAndView = new ModelAndView("gamaray");

		final long participantId = participantService.getParticipant(data.getPlayer());
		final Quest quest = questService.getQuest(data.getQuestId());
		final long participationId = questService.participateInQuest(participantId, quest);

		participantService.addParticipationLog(participationId, data.getTime(), data.getEvent(), data.getLocation());

		final Progress progress = participantService.getProgress(participationId);
      final Map<MultiKey,Integer> answers=participantService.getAnswers(quest.getId());

		questService.getQuest(data.getQuestId(), new TransactionalCallback<Quest>() {

			@Override
			public void onResult(Quest result) {
//				GeometryFactory factory = new GeometryFactory();
//				factory.createPoint(new Coordinate(data.getLon(), data.getLat()));
				final Dimension dimension = DimensionFactory.getInstance(result, data, configuration, progress, answers,participantId);
				modelAndView.addObject(dimension);
			}
		});

		return modelAndView;
	}

	private ModelAndView onPressEvent(final GamarayDataBean data) {
		if (!StringUtils.hasText(data.getEventSrc())) {
			throw new IllegalArgumentException("event must have a source!");
		}

		final ModelAndView modelAndView = new ModelAndView("gamaray");

		final long participantId = participantService.getParticipant(data.getPlayer());
		final Quest quest = questService.getQuest(data.getQuestId());
		final long participationId = questService.participateInQuest(participantId, quest);
		final Point loc = GeomUtil.createJTSPoint(data.getLat(), data.getLon());

		if (StringUtils.hasText(data.getAnswer())) {
			participantService.addParticipationLogAnswer(participationId, data.getTime(), data.getEvent(), loc, data.getEventSrc(), data.getAnswer());
		} else {
			participantService.addParticipationLog(participationId, data.getTime(), data.getEvent(), loc);
		}

		final Map<MultiKey,Integer> answers=participantService.getAnswers(quest.getId());
		final Progress progress = participantService.getProgress(participationId);
		questService.getQuest(data.getQuestId(), new TransactionalCallback<Quest>() {

			@Override
			public void onResult(Quest result) {
//				GeometryFactory factory = new GeometryFactory();
//				factory.createPoint(new Coordinate(data.getLon(), data.getLat()));
				final Dimension dimension = DimensionFactory.getInstance(result, data, configuration, progress,answers,participantId );
				modelAndView.addObject(dimension);
			}
		});

		return modelAndView;
	}

}
