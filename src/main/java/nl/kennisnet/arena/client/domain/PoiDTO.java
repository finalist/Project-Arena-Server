package nl.kennisnet.arena.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.elements.Element;

public class PoiDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String typeName;
	private Double radius;
	private Double visibleRadius;
	private Double alt;
	private SimplePoint point;
	private Long id;
	private List<Element> elements = new ArrayList<Element>();
	private Integer score;
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public PoiDTO() {
		super();
	}
	
	public PoiDTO(String name, String typeName) {
		super();
		this.name = name;
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Double getVisibleRadius() {
		return visibleRadius;
	}

	public void setVisibleRadius(Double visibleRadius) {
		this.visibleRadius = visibleRadius;
	}

	public Double getAlt() {
		return alt;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	public SimplePoint getPoint() {
		return point;
	}

	public void setPoint(SimplePoint point) {
		this.point = point;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}


	
	
}
