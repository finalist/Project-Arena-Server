package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Participant implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
//	private String uid;
	@Column(nullable = false)
	private String name;
	
	private String hexColor;
	
	@OneToMany
	private List<Participation> participations = new ArrayList<Participation>();
	
	public Participant() {
		
	}
	
	public Participant(String name) {
//		this.uid = uid;
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
//	public String getUid() {
//		return uid;
//	}

	public String getName() {
		return name;
	}

	public List<Participation> getParticipations() {
		return participations;
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

//	public void setUid(String uid) {
//		this.uid = uid;
//	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
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
