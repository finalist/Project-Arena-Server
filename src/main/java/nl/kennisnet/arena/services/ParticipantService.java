package nl.kennisnet.arena.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.AnswerDTO;
import nl.kennisnet.arena.client.domain.AnswerDTO.AnswerType;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.ParticipantAnswer.Result;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.PositionableCollectionHelper;
import nl.kennisnet.arena.model.Progress;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.services.factories.DTOFactory;
import nl.kennisnet.arena.services.support.HibernateAwareService;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Point;

@Service("participantService")
@Transactional
public class ParticipantService extends HibernateAwareService {
	private final Logger log = Logger.getLogger(ParticipantService.class);

	public void getParticipant(final String name,
			final TransactionalCallback<Participant> callback) {
		callback.onResult(get(Participant.class, getParticipantId(name)));
	}

	public void createParticipantIfNotPresent(final String name,
			final String color) {
		Criteria criteria = getSession().createCriteria(Participant.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.setMaxResults(1);

		List<Participant> participants = criteria.list();
		if (participants.size() == 0) {
			Participant participant = new Participant(name);
			participant.setHexColor(color);
			merge(participant);
			log.info("==> team created: " + name);
		} else {
			log.info("==> team was present: " + name);
		}
	}

	@SuppressWarnings("unchecked")
	public long getParticipantId(final String name) {
		Criteria criteria = getSession().createCriteria(Participant.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.setMaxResults(1);

		List<Participant> participants = criteria.list();
		if (participants.size() > 0) {
			return participants.get(0).getId();
		} else {
			Participant participant = new Participant(name);
			return merge(participant).getId();
		}
	}

	@SuppressWarnings("unchecked")
	public Participant getParticipant(final String name) {
		Criteria criteria = getSession().createCriteria(Participant.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.setMaxResults(1);

		List<Participant> participants = criteria.list();
		if (participants.size() > 0) {
			return participants.get(0);
		} else {
			Participant participant = new Participant(name);
			return merge(participant);
		}
	}

	public ParticipationLog addParticipationLog(final long participationId,
			final long time, final String action, final Point location) {
		Participation participation = get(Participation.class, participationId);
		ParticipationLog participationLog = new ParticipationLog(participation,
				new Date(time), action, location);
		return merge(participationLog);
	}

	public ParticipationLog addParticipationLogPress(
			final long participationId, final Long time, final String action,
			final Point location, final Positionable positionable) {
		Participation participation = get(Participation.class, participationId);
		ParticipationLog participationLog = new ParticipationLog(participation,
				new Date(time), action, location, positionable);
		return merge(participationLog);
	}

	public ParticipationLog addParticipationLogAnswer(
			final long participationId, final Long time, final String action,
			Point location, final Question question, final String answer) {
		Participation participation = get(Participation.class, participationId);

		if(location == null){
			location = question.getLocation().getPoint();
		}
		
		ParticipationLog participationLog = new ParticipationLog(participation,
				new Date(time), action, location, question, answer);
		return merge(participationLog);
	}

	public Progress getProgress(final long participationId) {
		Participation p = get(Participation.class, participationId);
		Quest quest = p.getQuest();

		List<ParticipationLog> participationLogs = p.getParticipationLogs();
		// int count = 0;
		Set<Long> counted = new HashSet<Long>();
		for (ParticipationLog log : participationLogs) {
			Positionable positionable = log.getPositionable();
			if (positionable instanceof Question) {
				// if (!counted.contains(positionable.getId())) {
				// count++;
				counted.add(positionable.getId());
				// }
			}
		}

		return new Progress(counted.size(), PositionableCollectionHelper
				.filter(quest.getPositionables(), Question.class).all().size());
	}

	private List<ParticipationLog> getParticipationLogs(Long questId) {
		// TODO : add ordering by time.
		Criteria criteria = getSession().createCriteria(ParticipationLog.class)
				.createCriteria("participation").createCriteria("quest")
				.add(Restrictions.eq("id", questId));
		List<ParticipationLog> log = criteria.list();
		return log;
	}

	public Map<MultiKey, Integer> getAnswers(Long questId) {
		Map<MultiKey, Integer> teamItemAnswers = new HashMap<MultiKey, Integer>();
		for (ParticipationLog log : getParticipationLogs(questId)) {
			if (log.getPositionable() != null
					&& log.getParticipation().getParticipant() != null) {
				MultiKey teamItemKey = new MultiKey(log.getPositionable()
						.getId(), log.getParticipation().getParticipant()
						.getId());
				try{
				teamItemAnswers.put(teamItemKey,
						Integer.valueOf(log.getAnswer().substring(1, 2)));
				}catch(NumberFormatException ne){
				}
			}
		}
		return teamItemAnswers;
	}

	public Map<Long, Integer> getPositionableScores(Long questId) {
		Map<MultiKey, Integer> answers = getAnswers(questId);
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		Quest quest = get(Quest.class, questId);

		for (MultiKey teamItem : answers.keySet()) {
			Long positionableId = (Long) teamItem.getKey(0);
			if (result.get(positionableId) == null) {
				result.put(positionableId, Integer.valueOf(0));
			}
			// Ignore answers of deleted questions.
			if (getQuestion(positionableId, quest) != null) {
				if (answers.get(teamItem).equals(
						getQuestion(positionableId, quest).getCorrectAnswer())) {
					result.put(positionableId, result.get(positionableId) + 1);
				}
			}
		}
		return result;
	}

	public Map<Long, Integer> getTeamScores(Long questId) {
		Map<MultiKey, Integer> answers = getAnswers(questId);
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		Quest quest = get(Quest.class, questId);

		for (MultiKey teamItem : answers.keySet()) {
			Long teamId = (Long) teamItem.getKey(1);
			Long positionableId = (Long) teamItem.getKey(0);
			if (result.get(teamId) == null) {
				result.put(teamId, Integer.valueOf(0));
			}
			// Check if the question was not deleted after answering the
			// question.
			if (getQuestion(positionableId, quest) != null) {
				if (answers.get(teamItem).equals(
						getQuestion(positionableId, quest).getCorrectAnswer())) {
					result.put(teamId, result.get(teamId) + 1);
				}
			}
		}
		return result;
	}

	public Question getQuestion(Long id, Quest quest) {
		if (quest != null && quest.getPositionables() != null) {
			for (Positionable positionable : quest.getPositionables()) {
				if (positionable.getId().equals(id)
						&& positionable instanceof Question) {
					return (Question) positionable;
				}
			}
		}
		return null;
	}

	public Set<TeamDTO> getAllParticipants() {
		Criteria criteria = getSession().createCriteria(Participant.class);
		List<Participant> participants = criteria.list();
		Set<TeamDTO> result = new HashSet<TeamDTO>();
		for (Participant participant : participants) {
			result.add(DTOFactory.create(participant));
		}
		return result;

	}

	public void storeParticipationAnswer(long participationId,
			Question question, int answer) {
		ParticipantAnswer participantAnswer = new ParticipantAnswer();
		participantAnswer.setAnswer(answer);
		Participation participation = get(Participation.class, participationId);
		participantAnswer.setParticipation(participation);
		participantAnswer.setQuestion(question);
		if(answer == question.getCorrectAnswer()){
			participantAnswer.setResult(Result.CORRECT.name());
		}else{
			participantAnswer.setResult(Result.INCORRECT.name());
		}
		merge(participantAnswer);
	}
	
	public void storeParticipationTextAnswer(long participationId,
			Question question, String textAnswer){
		ParticipantAnswer participantAnswer = new ParticipantAnswer();
		participantAnswer.setTextAnswer(textAnswer);
		Participation participation = get(Participation.class, participationId);
		participantAnswer.setParticipation(participation);
		participantAnswer.setQuestion(question);
		participantAnswer.setResult(Result.ANSWERED.name());
		merge(participantAnswer);
	}

	public ParticipantAnswer getParticipationAnswer(long participationId,
			Question question) {
		List<ParticipantAnswer> participants = question.getParticipantAnswers();
		for (ParticipantAnswer p : participants){
			if(p.getParticipationtId() == (participationId) && p.getQuestion().equals(question)){
				getSession().evict(participants.get(0));
				getSession().refresh(participants.get(0));
				return p;
			}			
		}		
		return null;
	}

	public LogDTO getParticipationLog(final Long questId) {
		List<ParticipationLog> log = getParticipationLogs(questId);

		Quest quest = (Quest) getSession().get(Quest.class, questId);

		List<ActionDTO> actions = new ArrayList<ActionDTO>(log.size());
		Set<TeamDTO> teams = new HashSet<TeamDTO>();

		for (ParticipationLog participationLog : log) {
			actions.add(DTOFactory.create(participationLog));
			TeamDTO team = DTOFactory.create(participationLog
					.getParticipation().getParticipant());
			teams.add(team);
		}

		Map<Long, Integer> teamScores = getTeamScores(questId);
		for (TeamDTO teamDTO : teams) {
			teamDTO.setScore(teamScores.get(teamDTO.getId()));
			if (teamDTO.getScore() == null) {
				teamDTO.setScore(0);
			}
		}

		List<QuestItemDTO> items = DTOFactory.create(quest).getItems();
		Map<Long, Integer> itemScores = getPositionableScores(quest.getId());

		for (QuestItemDTO questItemDTO : items) {
			questItemDTO.setScore(itemScores.get(questItemDTO.getId()));
			if (questItemDTO.getScore() == null) {
				questItemDTO.setScore(0);
			}

		}

		return new LogDTO(actions, teams, items);
	}
	
	public List<AnswerDTO> getAnswerDTO(final long questId){
		List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
		Quest quest = (Quest) getSession().get(Quest.class, questId);
		for(Positionable positionable : quest.getPositionables()){
			if(positionable instanceof Question){
				Question question = (Question)positionable;
				for(ParticipantAnswer participantAnswer : question.getParticipantAnswers()){
					AnswerDTO answerDTO = new AnswerDTO();
					if(question.getQuestionTypeAsEnum() == Question.TYPE.OPEN_QUESTION){
						answerDTO.setTextAnswer(participantAnswer.getTextAnswer());
						answerDTO.setAnswerType(AnswerType.TEXT_ANSWER);
					}
					else{
						answerDTO.setAnswer(participantAnswer.getAnswer());		
						answerDTO.setAnswerType(AnswerType.MULTIPLE_CHOICE);
					}
					answerDTO.setPlayerColor(participantAnswer.getParticipation().getParticipant().getHexColor());
					answerDTO.setPlayerName(participantAnswer.getParticipation().getParticipant().getName());
					answerDTO.setQuestionName(question.getName());
					answerDTO.setQuestionDescription(question.getText());
					answerDTO.setResult(participantAnswer.getResult());
					answerDTO.setQuestId(questId);
					answerDTO.setQuestionId(participantAnswer.getQuestion().getId());
					answerDTO.setParticipationId(participantAnswer.getParticipation().getId());
					answerDTOs.add(answerDTO);
				}
			}
		}
		return answerDTOs;
	}

	public void clearQuestLog(final Long questId) {
		List<ParticipationLog> log = getParticipationLogs(questId);
		for (ParticipationLog participationLog : log) {
			getSession().delete(participationLog);
		}
	}
	
	public String getImageUrl(long imageId){
		Image image = get(Image.class, imageId);		
		return image.getUrl();
	}
	
	public Information getInformation(long informationId){
		Information information = get(Information.class, informationId);
		return information;
	}

	public AnswerDTO updateAnswerDto(AnswerDTO answerDto, Quest quest) {
		ParticipantAnswer participantAnswer = getParticipationAnswer(answerDto.getParticipationId(), 
				getQuestion(answerDto.getQuestionId(), quest));
		participantAnswer.setResult(answerDto.getResult());
		merge(participantAnswer);
		return answerDto;
	}
}
