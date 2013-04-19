package nl.kennisnet.arena.model;

import javax.persistence.Entity;

@Entity
public class Information extends ContentElement {

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
