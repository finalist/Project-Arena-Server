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

/**
 * A gameround model, is responsible for multiple rounds in a single quest
 * @author A. Egal
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"name", "quest_id"})})
public class Round {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
		
	private String name;
	
	@ManyToOne()
	private Quest quest;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="round")
	private List<ParticipantAnswer> participantAnswers = new ArrayList<ParticipantAnswer>();
		
	public Round(Integer id, String name, Quest quest) {
		this.id = id;
		this.name = name;
		this.quest = quest;
	}
	
	public Round(){
		
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public Quest getQuest() {
		return quest;
	}
	
	public void setQuest(Quest quest) {
		this.quest = quest;
	}
	
	public List<ParticipantAnswer> getParticipantAnswers() {
		return participantAnswers;
	}
	
	public void setParticipantAnswers(List<ParticipantAnswer> participantAnswers) {
		this.participantAnswers = participantAnswers;
	}
	
	public void clearRound() {
		participantAnswers.clear();
	}
	
	public void addParticipantAnswer(ParticipantAnswer participantAnswer){
		participantAnswers.add(participantAnswer);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Round){
			if(name.equals(((Round)obj).getName()) &&
					quest.equals(((Round)obj).getQuest())){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
