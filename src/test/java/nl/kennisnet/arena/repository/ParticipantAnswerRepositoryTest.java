package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Picture;

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
public class ParticipantAnswerRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	PictureRepository repository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Picture createObject(String name) {
		Picture picture = new Picture();
		picture.setName(name);
		picture = repository.merge(picture);
		return picture;
	}
	
	@Test
	public void testCreate() {
		Picture aPicture = createObject("Schriek.jpg");
		assertThat(aPicture, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Picture aPicture = createObject("Henk.jpg");
		Picture receivedPicture = repository.get(aPicture.getId());		
		assertThat(receivedPicture, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		List<Picture> listOfCreatedPictures = new ArrayList<Picture>();
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			Picture picture = createObject("Henk" + count + ".jpg");
			listOfCreatedPictures.add(picture);
		}
		assertThat(repository.getAll(), is(listOfCreatedPictures));
	}
	
	@Test
	public void testDelete() {
		List<Picture> listToDelete = new ArrayList<Picture>();
		Picture picture1 = createObject("Jaap.jpg");
		Picture picture2 = createObject("Ali.jpg");
		Picture picture3 = createObject("Joop.jpg");
		long id1 = picture1.getId();
		long id2 = picture2.getId();
		long id3 = picture3.getId();
		listToDelete.add(picture1);
		listToDelete.add(picture3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
}
