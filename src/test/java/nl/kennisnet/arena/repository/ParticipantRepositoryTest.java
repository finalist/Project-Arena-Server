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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/configuration.xml",
		"classpath:/integration.xml", "classpath:/integration-test.xml"})
@Transactional
public class ParticipantRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	ParticipantRepository repository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Participant createObject(String name) {
		Participant participant = new Participant();
		participant.setName(name);
		participant = repository.merge(participant);
		return participant;
	}
	
	@Test
	public void testCreate() {
		Participant aParticipant = createObject("Schriek");
		assertThat(aParticipant, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Participant aParticipant = createObject("Henk");
		Participant receivedParticipant = repository.get(aParticipant.getId());		
		assertThat(receivedParticipant, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<Participant> listOfCreatedParticipants = new ArrayList<Participant>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			Participant participant = createObject("Henk" + count);
			listOfCreatedParticipants.add(participant);
		}
		assertThat(repository.getAll(), is(listOfCreatedParticipants));
	}
	
	@Test
	public void testDelete() {
		List<Participant> listToDelete = new ArrayList<Participant>();
		Participant participant1 = createObject("Jaap");
		Participant participant2 = createObject("Ali");
		Participant participant3 = createObject("Joop");
		long id1 = participant1.getId();
		long id2 = participant2.getId();
		long id3 = participant3.getId();
		listToDelete.add(participant1);
		listToDelete.add(participant3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
	@Test
	public void testFindParticipant() {
		Participant participant1 = createObject("Hans");
		Participant participant2 = createObject("Fatima");
		Participant participant3 = createObject("Hanoi");
		
		assertThat(repository.findParticipant("Hans"), is(participant1));
		assertThat(repository.findParticipant("Hansz"), is(nullValue()));
		assertThat(repository.findParticipant("Fatima"), is(participant2));
		assertThat(repository.findParticipant("Fatima"), is(not(participant1)));
		assertThat(repository.findParticipant("Hanoi"), is(participant3));
	}
	
}