package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;

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
	
	public Round(Integer id, String name, Quest quest) {
		super();
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Round){
			Round other = (Round) obj;
			return new EqualsBuilder().append(this.name, other.name).append(this.quest, other.quest).isEquals();
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
