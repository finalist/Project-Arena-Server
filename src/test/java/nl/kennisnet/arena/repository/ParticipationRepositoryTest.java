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
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.repository.ParticipationRepository;

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
public class ParticipationRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	ParticipationRepository repository;
	
	@Autowired
	QuestRepository qRepository;
	
	@Autowired
	ParticipantRepository pRepository;
	
	@Autowired
	RoundRepository rRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}


	private Participation createObject(String name, String email) {
		Participation participation = new Participation();
		Quest quest = new Quest();
		quest.setEmailOwner(email);
		quest = qRepository.merge(quest);
		Participant participant = new Participant(name);
		participant = pRepository.merge(participant);
		Round round = new Round();
		round.setQuest(quest);
		round = rRepository.merge(round);
		participation.setParticipant(participant);
		participation.setQuest(quest);
		participation.setRound(round);
		participation = repository.merge(participation);
		return participation;
	}
	
	@Test
	public void testCreate() {
		Participation aParticipation = createObject("Hans", "hansje@hanz.nl");
		assertThat(aParticipation, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		String name = "Hans";
		String email = "hansje@hanz.nl";
		Participation aParticipation = createObject(name, email);
		Participation receivedParticipation = repository.get(aParticipation.getId());
		Quest receivedQuest = receivedParticipation.getQuest();
		Participant receivedParticipant = receivedParticipation.getParticipant();
		Round receivedRound = receivedParticipation.getRound();
		assertThat(receivedParticipation, is(not(nullValue())));
		assertThat(receivedQuest, is(not(nullValue())));
		assertThat(receivedQuest.getEmailOwner(), is(email));
		assertThat(receivedParticipant, is(not(nullValue())));
		assertThat(receivedParticipant.getName(), is(name));
		assertThat(receivedRound, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<Participation> listOfCreatedParticipations = new ArrayList<Participation>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			Participation participation = createObject("Hans" + count, "hansje@hanz.nl");
			listOfCreatedParticipations.add(participation);
		}
		assertThat(repository.getAll(), is(listOfCreatedParticipations));
	}
	
	@Test
	public void testDelete() {
		List<Participation> listToDelete = new ArrayList<Participation>();
		Participation participation1 = createObject("Hansje", "hansje@hanz.nl");
		Participation participation2 = createObject("Ali", "ali@hanz.nl");
		Participation participation3 = createObject("Kees", "keez@hanz.nl");
		long id1 = participation1.getId();
		long id2 = participation2.getId();
		long id3 = participation3.getId();
		listToDelete.add(participation1);
		listToDelete.add(participation3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
	@Test
	public void testFindParticipation() {
		
		Participation participation1 = new Participation();
		Quest quest1 = new Quest();
		quest1.setEmailOwner("eenEmail@email.nl");
		quest1 = qRepository.merge(quest1);
		Participant participant1 = new Participant("Henko");
		participant1 = pRepository.merge(participant1);
		Round round1 = new Round();
		round1.setQuest(quest1);
		round1 = rRepository.merge(round1);
		participation1.setParticipant(participant1);
		participation1.setQuest(quest1);
		participation1.setRound(round1);
		//participation1.setScore(1);
		participation1 = repository.merge(participation1);
		
		Participation participation2 = new Participation();
		Quest quest2 = new Quest();
		quest2.setEmailOwner("hallo@email.nl");
		quest2 = qRepository.merge(quest2);
		Participant participant2 = new Participant("Henkzzz");
		participant2 = pRepository.merge(participant2);
		Round round2 = new Round();
		round2.setQuest(quest2);
		round2 = rRepository.merge(round2);
		participation2.setParticipant(participant2);
		participation2.setQuest(quest2);
		participation2.setRound(round2);
		//participation1.setScore(1);
		participation2 = repository.merge(participation2);
		
		Participation participation3 = new Participation();
		Quest quest3 = new Quest();
		quest3.setEmailOwner("eenEmail@email.nl");
		quest3 = qRepository.merge(quest3);
		Participant participant3 = new Participant("Henko");
		participant3 = pRepository.merge(participant3);
		Round round3 = new Round();
		round3.setQuest(quest3);
		round3 = rRepository.merge(round3);
		participation3.setParticipant(participant3);
		participation3.setQuest(quest3);
		participation3.setRound(round3);
		//participation1.setScore(1);
		participation3 = repository.merge(participation3);
		
		Participation foundParticipation = repository.findParticipation(participant2, quest2, round2);
		assertThat(foundParticipation, is(participation2));
	}
}
