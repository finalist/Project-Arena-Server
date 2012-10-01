package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Round;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/configuration.xml",
		"classpath:/integration.xml", "classpath:/integration-test.xml",
		"classpath:/services.xml", "classpath:arena-servlet-test.xml" })
@Transactional
public class ParticipationLogRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	ParticipationLogRepository repository;
	
	@Autowired
	ParticipationRepository participationRepository;
	
	@Autowired
	QuestRepository questRepository;
	
	@Autowired
	ParticipantRepository participantRepository;
	
	@Autowired
	RoundRepository roundRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private ParticipationLog createObject() {
		ParticipationLog participationLog = new ParticipationLog();
		participationLog = repository.merge(participationLog);
		return participationLog;
	}
	
	@Test
	public void testCreate() {
		ParticipationLog aParticipationLog = createObject();
		assertThat(aParticipationLog, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		ParticipationLog aParticipationLog = createObject();
		ParticipationLog receivedParticipationLog = repository.get(aParticipationLog.getId());		
		assertThat(receivedParticipationLog, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<ParticipationLog> listOfCreatedParticipationLogs = new ArrayList<ParticipationLog>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			ParticipationLog participationLog = createObject();
			listOfCreatedParticipationLogs.add(participationLog);
		}
		assertThat(repository.getAll(), is(listOfCreatedParticipationLogs));
	}
	
	@Test
	public void testDelete() {
		List<ParticipationLog> listToDelete = new ArrayList<ParticipationLog>();
		ParticipationLog participationLog1 = createObject();
		ParticipationLog participationLog2 = createObject();
		ParticipationLog participationLog3 = createObject();
		long id1 = participationLog1.getId();
		long id2 = participationLog2.getId();
		long id3 = participationLog3.getId();
		listToDelete.add(participationLog1);
		listToDelete.add(participationLog3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
	@Test
	public void testGetParticipationLogByQuestId() {
		ParticipationLog participationLog1 = new ParticipationLog();
		Participation participation1 = new Participation();
		Quest quest1 = new Quest();
		quest1.setEmailOwner("eenEmail@email.nl");
		quest1 = questRepository.merge(quest1);
		Participant participant1 = new Participant();
		participant1.setName("meneer 1");
		participant1 = participantRepository.merge(participant1);
		Round round1 = new Round();
		round1 = roundRepository.merge(round1);
		participation1.setQuest(quest1);
		participation1.setParticipant(participant1);
		participation1.setRound(round1);
		participation1 = participationRepository.merge(participation1);
		participationLog1.setParticipation(participation1);
		participationLog1 = repository.merge(participationLog1);
		assertThat(repository.getParticipationLogsByQuest(quest1.getId()).size(), is(not(0)));
	}
	
}
