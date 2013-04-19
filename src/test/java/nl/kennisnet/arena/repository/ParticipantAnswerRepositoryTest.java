package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import geodb.GeoDB;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.ContentElement;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.ParticipantAnswer.Result;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Round;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration.xml",
        "classpath:/integration.xml", "classpath:/integration-test.xml"})
@Transactional
public class ParticipantAnswerRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private ParticipantAnswerRepository repository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Before
    public void setUp() throws Exception {
        GeoDB.InitGeoDB(dataSource.getConnection());
    }

    @Test
    public void create_participant_answer_makes_element() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());

        ParticipantAnswer participantAnswer = createParticipantAnswer(participation, question, "Answer 1");
        assertThat(participantAnswer.getId(), is(not(nullValue())));
        assertThat(participantAnswer.getParticipation().getId(), is(not(nullValue())));
        assertThat(participantAnswer.getParticipation().getRound().getId(), is(not(nullValue())));
    }

    @Test
    public void created_participant_answers_should_be_retreivable() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());
        ParticipantAnswer participantAnswer = createParticipantAnswer(participation, question, "Answer 1");
        List<ParticipantAnswer> answers = repository.getAll();
        assertThat(answers, hasSize(1));
        assertThat(answers, contains(participantAnswer));
    }

    @Test
	public void all_created_participant_answers_should_be_retreivable() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());
		List<ParticipantAnswer> listOfCreatedParticipantAnswers = new ArrayList<ParticipantAnswer>();
		for (int count=0; count<10; count++) {
			listOfCreatedParticipantAnswers.add(createParticipantAnswer(participation,question,"Answer "+count));
		}
		assertThat(repository.getAll(), is(listOfCreatedParticipantAnswers));
	}

    @Test
    public void created_participant_answers_should_be_deletable() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());
        List<ParticipantAnswer> listToDelete = new ArrayList<ParticipantAnswer>();
        ParticipantAnswer pa1 = createParticipantAnswer(participation,question,"Answer 1");
        ParticipantAnswer pa2 = createParticipantAnswer(participation,question,"Answer 2");
        ParticipantAnswer pa3 = createParticipantAnswer(participation,question,"Answer 3");
        listToDelete.add(pa1);
        listToDelete.add(pa3);
        repository.delete(listToDelete);
        assertThat(repository.get(pa1.getId()), is(nullValue()));
        assertThat(repository.get(pa2.getId()), is(not(nullValue())));
        assertThat(repository.get(pa3.getId()), is(nullValue()));
    }

    @Test
    public void create_participant_answer_should_be_findable() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());

        ParticipantAnswer participantAnswer = new ParticipantAnswer();
        participantAnswer.setParticipation(participation);
        participantAnswer.setQuestion(question);
        participantAnswer = repository.merge(participantAnswer);

        repository.getAll();
        repository.getSession().clear();

        ParticipantAnswer foundAnswer = repository.findParticipantAnswer(participation, question);

        assertThat(participantAnswer.getId(), is(foundAnswer.getId()));
    }

    @Test
    public void two_participant_answers_should_be_findable() {

        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());

        ParticipantAnswer participantAnswer = new ParticipantAnswer();
        participantAnswer.setParticipation(participation);
        participantAnswer.setQuestion(question);
        participantAnswer.setTextAnswer("LOL1");
        participantAnswer = repository.merge(participantAnswer);

        ParticipantAnswer participantAnswer2 = new ParticipantAnswer();
        participantAnswer2.setParticipation(participation);
        participantAnswer2.setQuestion(question);
        participantAnswer2.setTextAnswer("LOL2");
        participantAnswer2 = repository.merge(participantAnswer2);

        repository.getAll();

        ParticipantAnswer foundAnswer = repository.findParticipantAnswers(question).get(1);

        assertThat(participantAnswer2, is(foundAnswer));
    }

    @Test
    public void testSaveAnswerApprove() {
        Participation participation = createParticipation();
        Question question = createQuestion(participation.getQuest());

        ParticipantAnswer answer1 = new ParticipantAnswer();
        answer1.setTextAnswer("The Answer");
        answer1.setQuestion(question);
        answer1.setParticipation(participation);
        answer1 = repository.merge(answer1);

        repository.getAll();
        repository.getSession().clear();

        // CHECK ANSWER
        ParticipantAnswer participantAnswer = repository.findParticipantAnswer(participation, question);
        participantAnswer.setResult(Result.INCORRECT.name());
        participantAnswer = repository.merge(participantAnswer);
        List<ParticipantAnswer> answers = repository.getAll();

        assertThat(answers, hasSize(1));
        assertThat(answers.get(0).getResult(), is(Result.INCORRECT.name()));

    }
    
    
    private ParticipantAnswer createParticipantAnswer(Participation participation, Question question, String answer) {
        ParticipantAnswer participantAnswer = new ParticipantAnswer();
        participantAnswer.setParticipation(participation);
        participantAnswer.setQuestion(question);
        participantAnswer.setTextAnswer(answer);
        return repository.merge(participantAnswer);

    }

    private Participation createParticipation() {
        Participation participation = new Participation();

        Round round = new Round();
        round = roundRepository.merge(round);

        Quest quest = new Quest();
        quest.setEmailOwner("testmail@test.nl");
        quest = questRepository.merge(quest);

        Participant participant = new Participant("testname");
        participant = participantRepository.merge(participant);
        participation.setParticipant(participant);
        participation.setQuest(quest);
        participation.setRound(round);
        participation = participationRepository.merge(participation);

        return participation;
    }

    private Question createQuestion(Quest quest) {

        Location location = new Location();
        location.setQuest(quest);
        location = locationRepository.merge(location);

        Question question = new Question();
        question.setLocation(location);

        List<ContentElement> questions = new ArrayList<ContentElement>();
        questions.add(question);
        location.setElements(questions);
        return questionRepository.merge(question);
    }


}
