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
import nl.kennisnet.arena.model.Positionable;
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
		"classpath:/integration.xml", "classpath:/integration-test.xml",
		"classpath:/services.xml", "classpath:arena-servlet-test.xml" })
@Transactional
public class PositionableRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	PositionableRepository repository;
	
	@Autowired
	QuestRepository qRepository;
	
	@Autowired
	LocationRepository lRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Positionable createObject(String email) {
		Positionable positionable = new Positionable();
		Quest quest = new Quest();
		quest.setEmailOwner(email);
		quest = qRepository.merge(quest);
		Location location = new Location();
		location = lRepository.merge(location);
		positionable.setLocation(location);
		positionable.setQuest(quest);
		positionable = repository.merge(positionable);
		return positionable;
	}
	
	@Test
	public void testCreate() {
		Positionable aPositionable = createObject("henk@henk.nl");
		assertThat(aPositionable, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		String email = "henk@henk.nl";
		Positionable aPositionable = createObject(email);
		Positionable receivedPositionable = repository.get(aPositionable.getId());
		Quest receivedQuest = receivedPositionable.getQuest();
		Location receivedLocation = receivedPositionable.getLocation();
		assertThat(receivedPositionable, is(not(nullValue())));
		assertThat(receivedLocation, is(not(nullValue())));
		assertThat(receivedQuest, is(not(nullValue())));
		assertThat(receivedQuest.getEmailOwner(), is(email));
	}
	
	@Test
	public void testGetAll() {
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			createObject("henk" + count + "@henk.nl");
		}
		assertThat(repository.getAll().size(), is(amount));
	}
	
	@Test
	public void testDelete() {
		List<Positionable> listToDelete = new ArrayList<Positionable>();
		Positionable positionable1 = createObject("henk1@henk.nl");
		Positionable positionable2 = createObject("henk2@henk.nl");
		Positionable positionable3 = createObject("henk3@henk.nl");
		long id1 = positionable1.getId();
		long id2 = positionable2.getId();
		long id3 = positionable3.getId();
		listToDelete.add(positionable1);
		listToDelete.add(positionable3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
}
