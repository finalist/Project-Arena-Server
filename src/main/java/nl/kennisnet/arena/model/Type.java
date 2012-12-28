package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Type {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long ID;

	@ManyToOne
	private Positionable poi;

	public Positionable getPoi() {
		if(poi == null) {
			poi = new Positionable();
		}
		return poi;
	}

	public void setPoi(Positionable poi) {
		this.poi = poi;
	}

	public Long getId() {
		return ID;
	}
}
