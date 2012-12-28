package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Positionable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Boolean consumable;
	
	@OneToOne(optional = false,orphanRemoval=true,cascade=CascadeType.ALL)
	private Location location;
	
	@ManyToOne(optional = false)
	private Quest quest;
	
	@OneToMany(mappedBy = "positionable", cascade = CascadeType.REMOVE, orphanRemoval=true)
	private List<ParticipationLog> participationlogs = new ArrayList<ParticipationLog>();
	
	@OneToMany(mappedBy = "poi", targetEntity = Type.class)
	private List<Type> elements = new LinkedList<Type>();
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getConsumable() {
		return consumable;
	}

	public void setConsumable(Boolean consumable) {
		this.consumable = consumable;
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
	
	public void setParticipationlogs(List<ParticipationLog> participationlogs) {
		this.participationlogs = participationlogs;
	}
	
	public List<ParticipationLog> getParticipationlogs() {
		return participationlogs;
	}
	
	public List<Type> getElements() {
		return elements;
	}

	public void setElements(List<Type> elements) {
		this.elements = elements;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Positionable){
			Positionable positionable = (Positionable)obj;
			
			if(positionable.getQuest().equals(this.getQuest()) && 
			   positionable.getName().equals(this.getName()) &&
			   positionable.getLocation().equals(this.getLocation())){
				return true;
			}		
			else if(this.getId() != null && positionable.getId() != null){
				if(this.getId().equals(positionable.getId())){
					return true;
				}
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getQuest().getId()).append(getName()).append(getLocation().getPoint()).toHashCode();
	}
}
