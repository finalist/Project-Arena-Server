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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"participant_id", "quest_id", "round_id"})})
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
	@ManyToOne
	private Round round;
	
	public Participation() {
		
	}
	
	public Participation(Participant participant, Quest quest, int score) {
		super();
		this.participant = participant;
		this.quest = quest;
		this.round = quest.getActiveRound();
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
		this.round = quest.getActiveRound();
	}
	
	public Round getRound() {
		return round;
	}
	
	public void setRound(Round round) {
		this.round = round;
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
