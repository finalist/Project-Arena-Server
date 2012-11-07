package nl.kennisnet.arena.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.repository.LocationRepository;
import nl.kennisnet.arena.repository.ParticipantRepository;
import nl.kennisnet.arena.repository.ParticipationLogRepository;
import nl.kennisnet.arena.repository.ParticipationRepository;
import nl.kennisnet.arena.repository.PositionableRepository;
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
	QuestService questService;
	
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
	
	@Autowired
	private PositionableRepository positionableRepository;
	
	@Autowired
	private LocationRepository locationRepository;

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
	
	private Quest createQuest(String email) {
		Quest quest1 = new Quest();
		quest1.setEmailOwner(email);
		quest1 = questRepository.merge(quest1);
		return quest1;
	}
	
	private Participation createParticipation(String name, String email) {
		Participation participation1 = new Participation();
		Quest quest1 = createQuest(email);
		Round round1 = new Round();
		round1 = roundRepository.merge(round1);
		participation1.setQuest(quest1);
		participation1.setRound(round1);
		Participant participant1 = new Participant();
		participant1.setName(name);
		participant1 = participantRepository.merge(participant1);
		participation1.setParticipant(participant1);
		participation1 = participationRepository.merge(participation1);
		List<Participation> plist = new ArrayList<Participation>();
		plist.add(participation1);
		participant1.setParticipations(plist);
		participant1 = participantRepository.merge(participant1);
		return participation1;
	}
	
	@Test
	public void testAddParticipationLog() {
		Participation p1 = createParticipation("hans", "hansje@email.nl");
		participantService.addParticipationLog(p1.getId(), 0, null, null);
		assertThat(participationLogRepository.getAll().size(), is(1));
	}
	
	@Test
	public void testAddParticipationLogPress() {
		Participation p1 = createParticipation("hanZs", "hansje@email.nl");
		participantService.addParticipationLogPress(p1.getId(), (long) 0, null, null, null);
		assertThat(participationLogRepository.getAll().size(), is(1));
	}
	
	@Test
	public void testGetProgress() {
		Participation p1 = createParticipation("hans", "hansje@email.nl");
		assertThat(participantService.getProgress(p1.getId()), is(not(nullValue())));
	}	
}
