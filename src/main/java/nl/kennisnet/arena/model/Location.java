package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
public class Location {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Type(type="org.hibernatespatial.GeometryUserType")
	private Point point;
	private Double alt;
	private Double radius;
	private Double visibleRadius;
	private Boolean onRadar;
	
	@OneToMany
	private List<Positionable> positionables = new ArrayList<Positionable>();
	
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

	public void setPositionables(List<Positionable> positionables) {
		this.positionables = positionables;
	}

	public List<Positionable> getPositionables() {
		return positionables;
	}

	public Double getAlt() {
		return alt;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Location){
			Location location = (Location)obj;
			if(location.getPoint().equals(this.getPoint())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getPoint()).toHashCode();
	}
	
	
	
}
