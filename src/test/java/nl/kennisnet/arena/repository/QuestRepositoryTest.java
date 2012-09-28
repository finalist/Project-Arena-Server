package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.repository.QuestRepository;

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
public class QuestRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	QuestRepository repository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Quest createObject(String name, String email) {
		Quest quest = new Quest();
		quest.setName(name);
		quest.setEmailOwner(email);
		quest = repository.merge(quest);
		return quest;
	}
	
	@Test
	public void testCreate() {
		Quest aQuest = createObject("Schriek", "henkie@henk.nl");
		assertThat(aQuest, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Quest aQuest = createObject("Henk", "schriek@schriek.nl");
		Quest receivedQuest = repository.get(aQuest.getId());		
		assertThat(receivedQuest, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<Quest> listOfCreatedQuests = new ArrayList<Quest>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			Quest quest = createObject("Henk" + count, "schriek@schriek.nl");
			listOfCreatedQuests.add(quest);
		}
		assertThat(repository.getAll(), is(listOfCreatedQuests));
	}
	
	@Test
	public void testDelete() {
		List<Quest> listToDelete = new ArrayList<Quest>();
		Quest quest1 = createObject("Jaap", "schriek@schriek.nl");
		Quest quest2 = createObject("Ali", "ali@ali.nl");
		Quest quest3 = createObject("Joop", "jopie@joop.nl");
		long id1 = quest1.getId();
		long id2 = quest2.getId();
		long id3 = quest3.getId();
		listToDelete.add(quest1);
		listToDelete.add(quest3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
}
