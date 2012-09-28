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


	private Participation createObject() {
		Participation participation = new Participation();
		participation = repository.merge(participation);
		return participation;
	}
	
	@Test
	public void testCreate() {
		Participation aParticipation = createObject();
		assertThat(aParticipation, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Participation aParticipation = createObject();
		Participation receivedParticipation = repository.get(aParticipation.getId());		
		assertThat(receivedParticipation, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<Participation> listOfCreatedParticipations = new ArrayList<Participation>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			Participation participation = createObject();
			listOfCreatedParticipations.add(participation);
		}
		assertThat(repository.getAll(), is(listOfCreatedParticipations));
	}
	
	@Test
	public void testDelete() {
		List<Participation> listToDelete = new ArrayList<Participation>();
		Participation participation1 = createObject();
		Participation participation2 = createObject();
		Participation participation3 = createObject();
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
	public void testRelatedObjects() {
		Participation participation = new Participation();
		Participant participant = new Participant();
		participant.setName("henk");
		participant = pRepository.merge(participant);
		Quest quest = new Quest();
		quest.setName("Ook henk");
		quest.setEmailOwner("henkie@henk.nl");
		quest = qRepository.merge(quest);
		participation.setParticipant(participant);
		participation.setQuest(quest);
		participation = repository.merge(participation);
		assertThat(participation.getParticipant(), is(participant));
		assertThat(participation.getQuest(), is(quest));
	}
	
	@Test
	public void testFindParticipation() {
		Participation participation1 = new Participation();
		Participant participant1 = new Participant();
		participant1.setName("henk");
		participant1 = pRepository.merge(participant1);
		
		Quest quest1 = new Quest();
		quest1.setName("Ook henk");
		quest1.setEmailOwner("henkie@henk.nl");
		quest1 = qRepository.merge(quest1);
		
		Round round1 = new Round();
		round1.setQuest(quest1);
		round1.setName("ronde1");
		round1 = rRepository.merge(round1);
		
		quest1.addRound(round1);
		quest1.setActiveRound(round1);
		quest1 = qRepository.merge(quest1);
		
		participation1.setParticipant(participant1);
		participation1.setQuest(quest1);
		participation1 = repository.merge(participation1);
		
		
		Participation participation2 = new Participation();
		Participant participant2 = new Participant();
		participant2.setName("henkie");
		participant2 = pRepository.merge(participant2);
		
		Quest quest2 = new Quest();
		quest2.setName("Ook henkz");
		quest2.setEmailOwner("henkie@henkz.nl");
		quest2 = qRepository.merge(quest2);
		
		Round round2 = new Round();
		round2.setName("ronde2");
		round2.setQuest(quest2);
		round2 = rRepository.merge(round2);
		
		quest2.addRound(round2);
		quest2.setActiveRound(round2);
		quest2 = qRepository.merge(quest2);
		
		participation2.setParticipant(participant2);
		participation2.setQuest(quest2);
		participation2 = repository.merge(participation2);
		
		Participation foundParticipation = repository.findParticipation(participant2, quest2, quest2.getActiveRound());
		assertThat(foundParticipation, is(participation2));
	}
}
