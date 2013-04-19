package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Quest;

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
public class LocationRepositoryTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LocationRepository repository;
	
	@Autowired
	private QuestRepository questRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Location createObject() {
        Quest quest = new Quest();
        quest.setEmailOwner("email@email.nl");
        quest = questRepository.merge(quest);
	    
		Location location = new Location();
		location.setQuest(quest);
		location = repository.merge(location);
		
		return location;
	}
	
	@Test
	public void testCreate() {
		Location aLocation = createObject();
		assertThat(aLocation, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Location aLocation = createObject();
		Location receivedLocation = repository.get(aLocation.getId());		
		assertThat(receivedLocation, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			createObject();
		}
		assertThat(repository.getAll().size(), is(amount));
	}
	
	@Test
	public void testDelete() {
		List<Location> listToDelete = new ArrayList<Location>();
		Location location1 = createObject();
		Location location2 = createObject();
		Location location3 = createObject();
		long id1 = location1.getId();
		long id2 = location2.getId();
		long id3 = location3.getId();
		listToDelete.add(location1);
		listToDelete.add(location3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
}
