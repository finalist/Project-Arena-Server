package nl.kennisnet.arena.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import geodb.GeoDB;

import javax.sql.DataSource;

import nl.kennisnet.arena.model.Image;
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
public class ImageRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	ImageRepository repository;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	QuestRepository questRepository;
	
	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());
	}

	private Image createObject(String url) {
        Quest quest = new Quest();
        quest.setEmailOwner("email@email.nl");
        quest = questRepository.merge(quest);
	    
		Location location = new Location();
        location.setQuest(quest);
		location = locationRepository.merge(location);
        
		Image image = new Image();
		image.setLocation(location);
		image.setUrl(url);
		image = repository.merge(image);
		return image;
	}
	
	@Test
	public void testCreate() {
		Image aImage = createObject("http://url.nl/1.jpg");
		assertThat(aImage, is(not(nullValue())));
	}
	
	@Test
	public void testGet() {
		Image aImage = createObject("http://url.nl/2.jpg");
		Image receivedImage = repository.get(aImage.getId());		
		assertThat(receivedImage, is(not(nullValue())));
	}
	
	@Test
	public void testGetAll() {
		int count;
		int amount = 10;
		for (count=0; count<amount; count++) {
			createObject("http://url.nl/" + count + ".jpg");
		}
		assertThat(repository.getAll().size(), is(amount));
	}
	
	@Test
	public void testDelete() {
		List<Image> listToDelete = new ArrayList<Image>();
		Image image1 = createObject("http://urlz.nl/1.jpg");
		Image image2 = createObject("http://urlz.nl/2.jpg");
		Image image3 = createObject("http://urlz.nl/3.jpg");
		long id1 = image1.getId();
		long id2 = image2.getId();
		long id3 = image3.getId();
		listToDelete.add(image1);
		listToDelete.add(image3);
		repository.delete(listToDelete);
		assertThat(repository.get(id1), is(nullValue()));
		assertThat(repository.get(id2), is(not(nullValue())));
		assertThat(repository.get(id3), is(nullValue()));
	}
	
}
