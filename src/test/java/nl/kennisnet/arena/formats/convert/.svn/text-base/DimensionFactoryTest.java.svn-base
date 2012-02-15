package nl.kennisnet.arena.formats.convert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.formats.Dimension;
import nl.kennisnet.arena.formats.OverlayTxt;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Progress;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.utils.GamarayDataBean;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.configuration.CompositeConfiguration;
import org.junit.Test;

public class DimensionFactoryTest {

	@Test
	public void testGetInstance() {
		Quest q = new Quest();
		GamarayDataBean gdb = new GamarayDataBean("peter", null, "init", "", 5.0, 51.0, 0.1, 0.0, 0.0, 320, 240, "uid12345", System.currentTimeMillis());
		CompositeConfiguration configuration = new CompositeConfiguration();
		configuration.setProperty("application.baseUrl", "http://t-arena.finalist.com/arena");
		
		Question question = new Question("question1", "answer1", "answer2");
		question.setId(1234L);
		question.setLocation(new Location(gdb.getLocation(), 0.1, 0.1));
		
		q.getPositionables().add(question);
		
		Image image = new Image();
		image.setId(123L);
		image.setLocation(new Location(gdb.getLocation(), 0.1, 0.1));
		image.setUrl("http://url.to/image.gif");
		
		q.getPositionables().add(image);
		
		
		Dimension dimension = DimensionFactory.getInstance(q, gdb, configuration, new Progress(0, 10), new HashMap<MultiKey, Integer>(),1000L);
		
		assertNotNull(dimension);
		assertNotNull(dimension.getFeatures());
		List<OverlayTxt> textOverlays = dimension.getOverlays().getTextOverlays();
		assertNotNull(textOverlays);
		assertThat(textOverlays.get(0).getText(), equalTo("question1"));
		assertThat(textOverlays.get(1).getText(), equalTo("[ ] answer1"));
		assertThat(textOverlays.get(2).getText(), equalTo("[ ] answer2"));		
		
		assertThat(dimension.getAssets().get(0).getId(), equalTo("asset_123"));
		assertThat(dimension.getFeatures().getImageFeatures().get(0).getId(), equalTo("image_123"));
	}

}
