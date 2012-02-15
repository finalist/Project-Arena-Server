package nl.kennisnet.arena.model;

import javax.persistence.Entity;

@Entity
public class Image extends Positionable implements DomainObject {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}