package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Participation implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int score;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="participation")
	// TODO: should probably be a SortedSet with ordering on timestamp
	private List<ParticipationLog> participationLogs = new ArrayList<ParticipationLog>();
	@ManyToOne
	private Quest quest;
	@ManyToOne
	private Participant participant;
	
	public Participation() {
		
	}
	
	public Participation(Participant participant, Quest quest, int score) {
		super();
		this.participant = participant;
		this.quest = quest;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Participant getParticipant() {
		return participant;
	}

	public List<ParticipationLog> getParticipationLogs() {
		return participationLogs;
	}

	public void setParticipationLogs(List<ParticipationLog> participationLogs) {
		this.participationLogs = participationLogs;
	}
	
}
