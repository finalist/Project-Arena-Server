package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

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
public class RoundRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	RoundRepository repository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Round createObject() {
		Round round = new Round();
		round = repository.merge(round);
		return round;
	}
	
	@Test
	public void testCreate() {
		Round aRound = createObject();
		assertThat(aRound, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Round aRound = createObject();
		Round receivedRound = repository.get(aRound.getId());		
		assertThat(receivedRound, is(not(nullValue())));
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
		List<Round> listToDelete = new ArrayList<Round>();
		Round round1 = createObject();
		createObject();
		Round round3 = createObject();
		createObject();
		listToDelete.add(round1);
		listToDelete.add(round3);
		repository.delete(listToDelete);
		assertThat(repository.getAll().size(), is(2));
	}
	
}
