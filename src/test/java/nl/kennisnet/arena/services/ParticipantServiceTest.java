package nl.kennisnet.arena.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.repository.ParticipantRepository;
import nl.kennisnet.arena.repository.ParticipationLogRepository;
import nl.kennisnet.arena.repository.ParticipationRepository;
import nl.kennisnet.arena.repository.QuestRepository;
import nl.kennisnet.arena.repository.RoundRepository;

import org.junit.After;
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
public class ParticipantServiceTest {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ParticipantService participantService;
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private ParticipationRepository participationRepository;
	
	@Autowired
	private ParticipationLogRepository participationLogRepository;
	
	@Autowired
	private QuestRepository questRepository;

	@Autowired
	private RoundRepository roundRepository;

	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateNotPresentParticipant() {
		assertThat(participantRepository.getAll().size(), is(0));
		participantService.createParticipantIfNotPresent("Hans", null);
		assertThat(participantRepository.getAll().size(), is(1));
	}
	
	@Test
	public void testCreatePresentParticipant() {
		assertThat(participantRepository.getAll().size(), is(0));
		participantService.createParticipantIfNotPresent("Hans", null);
		assertThat(participantRepository.getAll().size(), is(1));
		participantService.createParticipantIfNotPresent("Hans", null);
		assertThat(participantRepository.getAll().size(), is(1));
	}

	@Test
	public void testFindExistingParticipantReturnsId() {
		participantService.createParticipantIfNotPresent("Hans", null);
		long id1 = participantRepository.getAll().get(0).getId();
		long id2 = participantService.getParticipantId("Hans");
		long id3 = participantService.getParticipantId("Hazns");
		assertThat(id2, is(id1));
		assertThat(id2, is(not(id3)));
		//Created 2nd because Hazns doesn't exists.
		assertThat(participantRepository.getAll().size(), is(2));
	}
	
	@Test
	public void testFindExistingParticipant() {
		participantService.createParticipantIfNotPresent("Hans", null);
		Participant p1 = participantRepository.getAll().get(0);
		Participant p2 = participantService.getParticipant("Hans");
		Participant p3 = participantService.getParticipant("Hazns");
		assertThat(p2, is(p1));
		assertThat(p2, is(not(p3)));
		//Created 2nd because Hazns doesn't exists.
		assertThat(participantRepository.getAll().size(), is(2));
	}
	
	@Test
	public void testAddParticipationLog() {
		//NOT WORKING YET
		Quest quest = new Quest("quest");
		quest.setEmailOwner("email@email.nl");
		quest = questRepository.merge(quest);
		Participant participant = new Participant("hans");
		participant = participantRepository.merge(participant);
		Round round = new Round();
		round = roundRepository.merge(round);
		Participation participation = new Participation();
		participation.setParticipant(participant);
		participation.setRound(round);
		participation.setQuest(quest);
		participation = participationRepository.merge(participation);
		
		participantService.addParticipationLog(participation.getId(), 0, null, null);
		assertThat(participationLogRepository.getAll().size(), is(1));
	}
}
