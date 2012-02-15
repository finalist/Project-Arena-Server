package nl.kennisnet.arena.utils;

import nl.kennisnet.arena.services.factories.GeomUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.vividsolutions.jts.geom.Point;

public class GamarayDataBean {
	
	private Long questId;
	private String player;
	private String answer;
	private String event;
	private String eventSrc;
	private double lat;
	private double lon;
	private double alt;
	private double bearing;
	private double pitch;
	private int width;
	private int height;
	private String uid;
	private Long time;
	
	public GamarayDataBean() {
		super();
	}

	public GamarayDataBean(String player, String answer, String event, String eventSrc, double lat,
			double lon, double alt, double bearing, double pitch, int width,
			int height, String uid, Long time) {
		super();
		this.player = player;
		this.answer = answer;
		this.event = event;
		this.eventSrc = eventSrc;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.bearing = bearing;
		this.pitch = pitch;
		this.width = width;
		this.height = height;
		this.uid = uid;
		this.time = time;
	}

	public String getPlayer() {
		return player;
	}
	
	public String getAnswer() {
		return answer;
	}

	public String getEvent() {
		return event;
	}

	public String getEventSrc() {
		return eventSrc;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getAlt() {
		return alt;
	}

	public double getBearing() {
		return bearing;
	}

	public double getPitch() {
		return pitch;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getUid() {
		return uid;
	}

	public Long getTime() {
		return time;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public void setAnswer(String a) {
		this.answer = a;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setEventSrc(String eventSrc) {
		this.eventSrc = eventSrc;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}
	
	public Point getLocation() {
		return GeomUtil.createJTSPoint(lat, lon);
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getQuestId() {
		return questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}		
}
