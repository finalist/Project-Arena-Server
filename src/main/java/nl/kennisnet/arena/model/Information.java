package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Information extends Positionable implements DomainObject {

	private String text;

	public Information() {
	   super();
	}
	
	public Information(String name, String text) {
		super();
		this.setName(name);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
