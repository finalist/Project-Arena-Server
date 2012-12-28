package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class Information extends Type {

	private String text;

	public Information() {
	   super();
	}
	
	public Information(String text) {
		super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
