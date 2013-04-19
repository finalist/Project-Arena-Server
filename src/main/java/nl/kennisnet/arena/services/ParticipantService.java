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
import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.model.ContentElement;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.ParticipantAnswer.Result;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Progress;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.repository.ImageRepository;
import nl.kennisnet.arena.repository.InformationRepository;
import nl.kennisnet.arena.repository.ParticipantAnswerRepository;
import nl.kennisnet.arena.repository.ParticipantRepository;
import nl.kennisnet.arena.repository.ParticipationLogRepository;
import nl.kennisnet.arena.repository.ParticipationRepository;
import nl.kennisnet.arena.repository.QuestRepository;
import nl.kennisnet.arena.services.factories.DTOFactory;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Point;

@Service("participantService")
@Transactional
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    QuestRepository questRepository;

    @Autowired
    ParticipationLogRepository participationLogRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    InformationRepository informationRepository;

    @Autowired
    ParticipantAnswerRepository participantAnswerRepository;

    private final Logger log = Logger.getLogger(ParticipantService.class);

    public void getParticipant(final String name,
            final TransactionalCallback<Participant> callback) {
        callback.onResult(participantRepository.get(getParticipantId(name)));
    }

    public void createParticipantIfNotPresent(final String name,
            final String color) {

        Participant participant = participantRepository.findParticipant(name);

        if (participant == null) {
            Participant newParticipant = new Participant(name);
            newParticipant.setHexColor(color);
            participantRepository.merge(newParticipant);
            log.info("==> team created: " + name);
        } else {
            log.info("==> team was present: " + name);
        }
    }

    public long getParticipantId(final String name) {
        Participant participant = participantRepository.findParticipant(name);
        if (participant != null) {
            return participant.getId();
        } else {
            Participant newParticipant = new Participant(name);
            return participantRepository.merge(newParticipant).getId();
        }
    }

    public Participant getParticipant(final String name) {
        Participant participant = participantRepository.findParticipant(name);
        if (participant != null) {
            return participant;
        } else {
            Participant newParticipant = new Participant(name);
            return participantRepository.merge(newParticipant);
        }
    }

    public ParticipationLog addParticipationLog(final long participationId,
            final long time, final String action, final Point location) {
        Participation participation = participationRepository
                .get(participationId);
        ParticipationLog participationLog = new ParticipationLog(participation,
                new Date(time), action, location);
        return participationLogRepository.merge(participationLog);
    }

    public ParticipationLog addParticipationLogPress(
            final long participationId, final Long time, final String action,
            final Point location, final ContentElement element) {
        Participation participation = participationRepository
                .get(participationId);
        ParticipationLog participationLog = new ParticipationLog(participation,
                new Date(time), action, location, element);
        return participationLogRepository.merge(participationLog);
    }

    public Progress getProgress(final long participationId) {
        Participation p = participationRepository.get(participationId);
        List<ParticipationLog> participationLogs = p.getParticipationLogs();

        Set<Long> counted = new HashSet<Long>();
        for (ParticipationLog log : participationLogs) {
            ContentElement element = log.getElement();
            if (element instanceof Question) {
                counted.add(element.getId());
            }
        }

        return new Progress(0, 100);
    }

    private List<ParticipationLog> getParticipationLogs(Long questId) {
        // TODO : add ordering by time.
        return participationLogRepository.getParticipationLogsByQuest(questId);
    }

    public Map<MultiKey, Integer> getAnswers(Long questId) {
        Map<MultiKey, Integer> teamItemAnswers = new HashMap<MultiKey, Integer>();
        for (ParticipationLog log : getParticipationLogs(questId)) {
            if (log.getElement() != null
                    && log.getParticipation().getParticipant() != null) {
                MultiKey teamItemKey = new MultiKey(log.getElement()
                        .getId(), log.getParticipation().getParticipant()
                        .getId());
                try {
                    teamItemAnswers.put(teamItemKey,
                            Integer.valueOf(log.getAnswer().substring(1, 2)));
                } catch (NumberFormatException ne) {
                }
            }
        }
        return teamItemAnswers;
    }

    public Map<Long, Integer> getPositionableScores(Long questId) {
        Map<Long, Integer> result = new HashMap<Long, Integer>();
        Quest quest = questRepository.get(questId);

        for (Question question : getQuestions(quest)) {
            for (ParticipantAnswer participantAnswer : participantAnswerRepository.findParticipantAnswers(question)) {
                long positionableId = participantAnswer.getQuestion().getId();
                if (result.get(positionableId) == null) {
                    result.put(positionableId, 0);
                }
            }

        }
        return result;
    }

    public Map<Long, Integer> getTeamScores(Long questId) {
        Map<Long, Integer> result = new HashMap<Long, Integer>();

        Quest quest = questRepository.get(questId);
        for (Question question : getQuestions(quest)) {
            for (ParticipantAnswer participantAnswer : participantAnswerRepository.findParticipantAnswers(question)) {

                long teamId = participantAnswer.getParticipation().getParticipant().getId();
                if (result.get(teamId) == null) {
                    result.put(teamId, 0);
                }
                if (participantAnswer.getResult().equals(ParticipantAnswer.Result.CORRECT.name())) {
                    result.put(teamId, result.get(teamId) + 1);
                }
            }
        }
        return result;
    }

    public Question getQuestion(Long id, Quest quest) {
        for (Question question : getQuestions(quest)) {
            if (question.getId().equals(id)) {
                return question;
            }
        }
        return null;
    }

    private List<Question> getQuestions(Quest quest) {
        List<Question> questions = new ArrayList<Question>();
        if (quest != null && quest.getLocations() != null) {
            for (Location location : quest.getLocations()) {
                for (ContentElement element : location.getElements()) {
                    if (element instanceof Question) {
                        questions.add((Question) element);
                    }
                }
            }
        }
        return questions;
    }

    public List<TeamDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.getAll();
        List<TeamDTO> result = new ArrayList<TeamDTO>();
        for (Participant participant : participants) {
            result.add(DTOFactory.create(participant));
        }
        return result;

    }

    public void storeParticipationAnswer(long participationId,
            Question question, int answer) {
        ParticipantAnswer participantAnswer = getParticipationAnswer(
                participationId, question);
        if (participantAnswer == null) {
            participantAnswer = new ParticipantAnswer();
        }
        participantAnswer.setAnswer(answer);
        Participation participation = participationRepository
                .get(participationId);
        participantAnswer.setParticipation(participation);
        participantAnswer.setQuestion(question);
        if (question.getCorrectAnswer() == null) {
            throw new IllegalArgumentException("No answer given");
        }
        if (answer == question.getCorrectAnswer()) {
            participantAnswer.setResult(Result.CORRECT.name());
        } else {
            participantAnswer.setResult(Result.INCORRECT.name());
        }
        participantAnswerRepository.merge(participantAnswer);
    }

    public void storeParticipationTextAnswer(long participationId,
            Question question, String textAnswer) {
        ParticipantAnswer participantAnswer = getParticipationAnswer(
                participationId, question);
        if (participantAnswer == null) {
            participantAnswer = new ParticipantAnswer();
        }
        participantAnswer.setTextAnswer(textAnswer);
        Participation participation = participationRepository
                .get(participationId);
        participantAnswer.setParticipation(participation);
        participantAnswer.setQuestion(question);
        participantAnswer.setResult(Result.ANSWERED.name());
        participantAnswerRepository.merge(participantAnswer);
    }

    public ParticipantAnswer getParticipationAnswer(long participationId,
            Question question) {
        Participation participation = participationRepository
                .get(participationId);
        return participantAnswerRepository.findParticipantAnswer(participation,
                question);
    }

    public LogDTO getParticipationLog(final Long questId) {
        List<ParticipationLog> log = getParticipationLogs(questId);

        Quest quest = (Quest) questRepository.get(questId);

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

        List<PoiDTO> items = DTOFactory.create(quest).getItems();
        Map<Long, Integer> itemScores = getPositionableScores(quest.getId());

        for (PoiDTO PoiDTO : items) {
            PoiDTO.setScore(itemScores.get(PoiDTO.getId()));
            if (PoiDTO.getScore() == null) {
                PoiDTO.setScore(0);
            }

        }

        return new LogDTO(actions, teams, items);
    }

    public List<AnswerDTO> getAnswerDTO(final long questId) {
        List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
        Quest quest = (Quest) questRepository.get(questId);
        for (Location location : quest.getLocations()) {

            for (ContentElement element : location.getElements()) {
                if (element instanceof Question) {
                    Question question = (Question) element;
                    for (ParticipantAnswer participantAnswer : participantAnswerRepository
                            .findParticipantAnswers(question)) {

                        AnswerDTO answerDTO = new AnswerDTO();
                        if (question.getQuestionTypeAsEnum() == Question.TYPE.OPEN_QUESTION) {
                            answerDTO.setTextAnswer(participantAnswer
                                    .getTextAnswer());
                            answerDTO.setAnswerType(AnswerType.TEXT_ANSWER);
                        } else {
                            answerDTO.setAnswer(participantAnswer.getAnswer());
                            answerDTO.setAnswerType(AnswerType.MULTIPLE_CHOICE);
                        }
                        answerDTO.setPlayerColor(participantAnswer
                                .getParticipation().getParticipant()
                                .getHexColor());
                        answerDTO.setPlayerName(participantAnswer
                                .getParticipation().getParticipant().getName());
                        // answerDTO.setQuestionName(question.getName());
                        answerDTO.setQuestionDescription(question.getText());
                        answerDTO.setResult(participantAnswer.getResult());
                        answerDTO.setQuestId(questId);
                        answerDTO.setQuestionId(participantAnswer.getQuestion()
                                .getId());
                        answerDTO.setParticipationId(participantAnswer
                                .getParticipation().getId());
                        answerDTO.setRound(new RoundDTO(participantAnswer
                                .getParticipation().getRound().getId(),
                                participantAnswer.getParticipation().getRound()
                                        .getName()));
                        answerDTOs.add(answerDTO);
                    }
                }
            }
        }
        return answerDTOs;
    }

    public void clearQuestLog(final Long questId) {
        List<ParticipationLog> log = getParticipationLogs(questId);
        for (ParticipationLog participationLog : log) {
            participationLogRepository.delete(participationLog);
        }
    }

    public String getImageUrl(long imageId) {
        Image image = imageRepository.get(imageId);
        return image.getUrl();
    }

    public Information getInformation(long informationId) {
        Information information = informationRepository.get(informationId);
        return information;
    }

    public AnswerDTO updateAnswerDto(AnswerDTO answerDto, Quest quest) {
        ParticipantAnswer participantAnswer = getParticipationAnswer(
                answerDto.getParticipationId(),
                getQuestion(answerDto.getQuestionId(), quest));
        participantAnswer.setResult(answerDto.getResult());
        participantAnswerRepository.merge(participantAnswer);
        return answerDto;
    }
}
