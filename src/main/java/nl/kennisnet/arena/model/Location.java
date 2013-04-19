package nl.kennisnet.arena.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
public class Location {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Type(type="org.hibernatespatial.GeometryUserType")
	private Point point;
	
	private Double alt;
	private Double radius;
	private Double visibleRadius;
	private Boolean onRadar;
	
	private String name;
	private Boolean consumable;
	
	@ManyToOne(optional = false)
	private Quest quest;
	
	@OneToMany(mappedBy = "location")
	private List<ContentElement> elements;	
	
	public Location() {
	}
	
	public Location(Point point, Double alt, Double radius, Double visibleRadius) {
		this.point = point;
		this.alt = alt;
		this.radius = radius;
		this.visibleRadius = visibleRadius;
		this.onRadar = true;
	}
	
	public Location(Point point, Double alt, Double radius, Double visibleRadius, Boolean onRadar) {
		this.point = point;
		this.alt = alt;
		this.radius = radius;
		this.visibleRadius = visibleRadius;
		this.onRadar = onRadar;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public Double getRadius() {
		return radius;
	}

	public Boolean getOnRadar() {
		return onRadar;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public void setOnRadar(Boolean onRadar) {
		this.onRadar = onRadar;
	}
	
	public void setVisibleRadius(Double visibleRadius) {
		this.visibleRadius = visibleRadius;
	}
	
	public Double getVisibleRadius() {
		return visibleRadius;
	}

	public Double getAlt() {
		return alt;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getConsumable() {
		return consumable;
	}

	public void setConsumable(Boolean consumable) {
		this.consumable = consumable;
	}

	public List<ContentElement> getElements() {
		return elements;
	}
	
	public void setElements(List<ContentElement> elements) {
		this.elements = elements;
	}
	
	public void setQuest(Quest quest) {
		this.quest = quest;
	}
	public Quest getQuest() {
		return quest;
	}
	
}
