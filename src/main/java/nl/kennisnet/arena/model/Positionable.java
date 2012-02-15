package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Positionable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Boolean consumable;
	
	@ManyToOne()
	@Cascade(CascadeType.ALL)
	private Location location;
	
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
	
	
}
