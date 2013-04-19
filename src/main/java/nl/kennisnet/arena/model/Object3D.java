package nl.kennisnet.arena.model;

import javax.persistence.Entity;

@Entity
public class Object3D extends ContentElement {

	private String url;
	private float scale;
	private boolean isBlended;
	private Float x, y, z;

	public Object3D() {
		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	public boolean isBlended() {
		return isBlended;
	}

	public void setBlended(boolean isBlended) {
		this.isBlended = isBlended;
	}

	public Float[] getRotation() {
		Float[] f = new Float[3];
		f[0] = x;
		f[1] = y;
		f[2] = z;

		return f;
	}

	public void setRotation(Float[] rotation) {
		x = rotation[0];
		y = rotation[1];
		z = rotation[2];
	}
}
