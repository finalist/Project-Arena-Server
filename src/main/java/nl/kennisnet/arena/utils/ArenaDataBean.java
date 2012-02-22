package nl.kennisnet.arena.utils;

import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.services.factories.GeomUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.vividsolutions.jts.geom.Point;

public class ArenaDataBean {

	private Long questId;
	private String player;
	private double lat;
	private double lon;
	private long participantId;
	private Quest quest;
	private Question question;
	private long participationId;
	private ParticipantService participantService;
	
	public ArenaDataBean() {
	}
	
	public ArenaDataBean(Long questId, String player, double lat, double lon,
			long participantId, Quest quest, Question question,
			long participationId) {
		super();
		this.questId = questId;
		this.player = player;
		this.lat = lat;
		this.lon = lon;
		this.participantId = participantId;
		this.quest = quest;
		this.question = question;
		this.participationId = participationId;
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

	public long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getParticipationId() {
		return participationId;
	}

	public void setParticipationId(long participationId) {
		this.participationId = participationId;
	}
	
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}
	
	public ParticipantService getParticipantService() {
		return participantService;
	}

	public Point getLocation() {
		return GeomUtil.createJTSPoint(lat, lon);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}		
	
}
