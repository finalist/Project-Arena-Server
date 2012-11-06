package nl.kennisnet.arena.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class Object3D extends Positionable implements DomainObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	private float schaal;
	private boolean isBlended;
	private ArrayList<Float> rotation;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setSchaal(float schaal) {
		this.schaal = schaal;
	}
	
	public float getSchaal() {
		return schaal;
	}

	public boolean isBlended() {
		return isBlended;
	}

	public void setBlended(boolean isBlended) {
		this.isBlended = isBlended;
	}

	public ArrayList<Float> getRotation() {
		return rotation;
	}

	public void setRotation(ArrayList<Float> rotation) {
		this.rotation = rotation;
	}
}
