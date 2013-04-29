package nl.kennisnet.arena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	private String hexColor;
	
	public Participant() {
		
	}
	
	public Participant(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Participant){
			Participant p = (Participant)obj;
			if(p.getId().equals(this.getId())){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}
	
	
}
