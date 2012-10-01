package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Information;
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
		"classpath:/integration.xml", "classpath:/integration-test.xml",
		"classpath:/services.xml", "classpath:arena-servlet-test.xml" })
@Transactional
public class InformationRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	InformationRepository repository;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	QuestRepository questRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Information createObject(String text) {
		Information information = new Information();
		Location location = new Location();
		location = locationRepository.merge(location);
		information.setLocation(location);
		Quest quest = new Quest();
		quest.setEmailOwner("email@email.nl");
		quest = questRepository.merge(quest);
		information.setQuest(quest);
		information.setText(text);
		information = repository.merge(information);
		return information;
	}
	
	@Test
	public void testCreate() {
		Information aInformation = createObject("Een tekst 1");
		assertThat(aInformation, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Information aInformation = createObject("Een tekst 2");
		Information receivedInformation = repository.get(aInformation.getId());		
		assertThat(receivedInformation, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			createObject("Een tekst " + count);
		}
		assertThat(repository.getAll().size(), is(amount));
	}
	
	@Test
	public void testDelete() {
		List<Information> listToDelete = new ArrayList<Information>();
		Information information1 = createObject("Een tekst 1");
		Information information2 = createObject("Een tekst 2");
		Information information3 = createObject("Een tekst 3");
		long id1 = information1.getId();
		long id2 = information2.getId();
		long id3 = information3.getId();
		listToDelete.add(information1);
		listToDelete.add(information3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
}
