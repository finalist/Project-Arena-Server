package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Positionable;

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
public class PositionableTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	PositionableRepository repository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Positionable createObject() {
		Positionable positionable = new Positionable();
		positionable.setName("Henk");
		positionable = repository.merge(positionable);
		return positionable;
	}
	
	@Test
	public void testCreate() {
		Positionable aPositionable = createObject();
		assertThat(aPositionable, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Positionable aPositionable = createObject();
		Positionable receivedPositionable = repository.get(aPositionable.getId());		
		assertThat(receivedPositionable, is(not(nullValue())));
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
		List<Positionable> listToDelete = new ArrayList<Positionable>();
		Positionable positionable1 = createObject();
		Positionable positionable2 = createObject();
		Positionable positionable3 = createObject();
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
