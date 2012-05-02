package nl.kennisnet.arena.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class YoutubeVideoTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleYoutubeURl() {
		String simpleUrl = "http://www.youtube.com/watch?v=8tPnX7OPo0Q";
		Video video = new Video();
		video.setVideoUrl(simpleUrl);
		
		String expectedResult = "8tPnX7OPo0Q";
		Assert.assertEquals(expectedResult, video.getVideoId());
	}
	
	@Test
	public void testWithOtherParamsInPrefix(){
		String simpleUrl = "http://www.youtube.com/watch?v=8tPnX7OPo0Q&c=55";
		Video video = new Video();
		video.setVideoUrl(simpleUrl);
		
		String expectedResult = "8tPnX7OPo0Q";
		Assert.assertEquals(expectedResult, video.getVideoId());
	}
	
	@Test
	public void testWithHashAtEnd(){
		String simpleUrl = "http://www.youtube.com/watch?v=8tPnX7OPo0Q#3333";
		Video video = new Video();
		video.setVideoUrl(simpleUrl);
		
		String expectedResult = "8tPnX7OPo0Q";
		Assert.assertEquals(expectedResult, video.getVideoId());
	}
	
	@Test
	public void testWithShortUrl(){
		String simpleUrl = "http://youtu.be/8tPnX7OPo0Q";
		Video video = new Video();
		video.setVideoUrl(simpleUrl);

		String expectedResult = "8tPnX7OPo0Q";
		Assert.assertEquals(expectedResult, video.getVideoId());
	}
	
	@Test
	public void testThumbnail(){
		String simpleUrl = "http://youtu.be/8tPnX7OPo0Q";
		Video video = new Video();
		video.setVideoUrl(simpleUrl);

		String expectedResult = "http://img.youtube.com/vi/8tPnX7OPo0Q/3.jpg";
		Assert.assertEquals(expectedResult, video.getThumbnailUrl());
	}
	
}
