package nl.kennisnet.arena.utils;

import nl.kennisnet.arena.services.factories.GeomUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.vividsolutions.jts.geom.Point;

public class ArenaDataBean {

	private Long questId;
	private String player;
	private String answer;
	private String event;
	private String eventSrc;
	private double lat;
	private double lon;
	private int width;
	private int height;
	private String uid;
	private Long time;

	public ArenaDataBean() {
	}
	
	public ArenaDataBean(Long questId, String player, String answer,
			String event, String eventSrc, double lat, double lon, int width,
			int height, String uid, Long time) {
		super();
		this.questId = questId;
		this.player = player;
		this.answer = answer;
		this.event = event;
		this.eventSrc = eventSrc;
		this.lat = lat;
		this.lon = lon;
		this.width = width;
		this.height = height;
		this.uid = uid;
		this.time = time;
	}

	public Long getQuestId() {
		return questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventSrc() {
		return eventSrc;
	}

	public void setEventSrc(String eventSrc) {
		this.eventSrc = eventSrc;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
	public Point getLocation() {
		return GeomUtil.createJTSPoint(lat, lon);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}		
	
}
