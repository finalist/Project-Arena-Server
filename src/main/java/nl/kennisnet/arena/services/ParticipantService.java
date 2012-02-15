package nl.kennisnet.arena.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.model.Participant;
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

   public void getParticipant(final String name, final TransactionalCallback<Participant> callback) {
      callback.onResult(get(Participant.class, getParticipant(name)));
   }

   public void createParticipantIfNotPresent(final String name, final String color) {
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
   public long getParticipant(final String name) {
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

   public ParticipationLog addParticipationLog(final long participationId, final long time, final String action,
         final Point location) {
      Participation participation = get(Participation.class, participationId);
      ParticipationLog participationLog = new ParticipationLog(participation, new Date(time), action, location);
      return merge(participationLog);
   }

   public ParticipationLog addParticipationLogPress(final long participationId, final Long time, final String action,
         final Point location, final Positionable positionable) {
      Participation participation = get(Participation.class, participationId);
      ParticipationLog participationLog = new ParticipationLog(participation, new Date(time), action, location, positionable);
      return merge(participationLog);
   }

   public ParticipationLog addParticipationLogAnswer(final long participationId, final Long time, final String action,
         final Point location, final String positionable, final String answer) {
      Participation participation = get(Participation.class, participationId);
      Long id = Long.valueOf(answer.substring(3, answer.length()));

      Criteria criteria = getSession().createCriteria(Question.class);
      criteria.add(Restrictions.eq("id", id));
      criteria.setMaxResults(1);

      List<Question> questions = criteria.list();
      Question question = new Question();
      if (questions.size() > 0) {
         question = questions.get(0);
      }

      ParticipationLog participationLog = new ParticipationLog(participation, new Date(time), action, location, question, answer);
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

      return new Progress(counted.size(), PositionableCollectionHelper.filter(quest.getPositionables(), Question.class).all()
            .size());
   }

   private List<ParticipationLog> getParticipationLogs(Long questId) {
      // TODO : add ordering by time.
      Criteria criteria = getSession().createCriteria(ParticipationLog.class).createCriteria("participation").createCriteria(
            "quest").add(Restrictions.eq("id", questId));
      List<ParticipationLog> log = criteria.list();
      return log;
   }

   public Map<MultiKey, Integer> getAnswers(Long questId) {
      Map<MultiKey, Integer> teamItemAnswers = new HashMap<MultiKey, Integer>();
      for (ParticipationLog log : getParticipationLogs(questId)) {
         if (log.getPositionable() != null && log.getParticipation().getParticipant() != null) {
            MultiKey teamItemKey = new MultiKey(log.getPositionable().getId(), log.getParticipation().getParticipant().getId());
            teamItemAnswers.put(teamItemKey, Integer.valueOf(log.getAnswer().substring(1, 2)));
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
         //Ignore answers of deleted questions.
         if (getQuestion(positionableId, quest)!=null){
            if (answers.get(teamItem).equals(getQuestion(positionableId, quest).getCorrectAnswer())) {
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
         //Check if the question was not deleted after answering the question.
         if (getQuestion(positionableId, quest)!=null){
            if (answers.get(teamItem).equals(getQuestion(positionableId, quest).getCorrectAnswer())) {
               result.put(teamId, result.get(teamId) + 1);
            }
         }
      }
      return result;
   }

   private Question getQuestion(Long id, Quest quest) {
      if (quest != null && quest.getPositionables() != null) {
         for (Positionable positionable : quest.getPositionables()) {
            if (positionable.getId().equals(id) && positionable instanceof Question) {
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

   public LogDTO getParticipationLog(final Long questId) {
      List<ParticipationLog> log = getParticipationLogs(questId);

      Quest quest = (Quest) getSession().get(Quest.class, questId);

      List<ActionDTO> actions = new ArrayList<ActionDTO>(log.size());
      Set<TeamDTO> teams = new HashSet<TeamDTO>();

      for (ParticipationLog participationLog : log) {
         actions.add(DTOFactory.create(participationLog));
         TeamDTO team = DTOFactory.create(participationLog.getParticipation().getParticipant());
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
   
   public void clearQuestLog(final Long questId){
      List<ParticipationLog> log = getParticipationLogs(questId);
      for (ParticipationLog participationLog : log) {
         getSession().delete(participationLog);
      }
   }
}
