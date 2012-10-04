package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import nl.kennisnet.arena.services.factories.GeomUtil;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

@Entity
public class Quest implements DomainObject {

	private static final double HOTZONE_RADIUS = 500.0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(nullable = false)
	private String emailOwner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "quest", orphanRemoval=true)
	private List<Positionable> positionables = new ArrayList<Positionable>();

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy="quest", orphanRemoval=true)
	private List<Round> rounds = new ArrayList<Round>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Round activeRound;
	
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Polygon border;

	public Quest() {
		super();
	}

	public Quest(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmailOwner(String emailOwner) {
		this.emailOwner = emailOwner;
	}

	public String getEmailOwner() {
		return emailOwner;
	}

	public List<Positionable> getPositionables() {
		return positionables;
	}

	/**
	 * Return the positiables that are visible on the radar.
	 * 
	 * @param selectedPoint
	 * @return
	 */
	public List<Positionable> getPositionablesForRadar(Point selectedPoint) {
		List<Positionable> filteredPositionables = new ArrayList<Positionable>();

		for (Positionable positionable : positionables) {
			Point point = positionable.getLocation().getPoint();

			double dist = GeomUtil.calculateDistanceInMeters(point,
					selectedPoint);
			if (dist <= positionable.getLocation().getRadius()
					&& dist > HOTZONE_RADIUS) {
				filteredPositionables.add(positionable);
			}
		}

		return filteredPositionables;
	}

	/**
	 * Return the positiables that are visible on the radar.
	 * 
	 * @param selectedPoint
	 * @return
	 */
	public List<Positionable> getVisiblePositionables(Point selectedPoint) {
		List<Positionable> filteredPositionables = new ArrayList<Positionable>();

		for (Positionable positionable : positionables) {
			Point point = positionable.getLocation().getPoint();

			double dist = GeomUtil.calculateDistanceInMeters(point,
					selectedPoint);
			// if (dist <= HOTZONE_RADIUS) {
			if (dist <= positionable.getLocation().getRadius()) {
				filteredPositionables.add(positionable);
			}
		}

		return filteredPositionables;
	}

	public void setPositionables(List<Positionable> positionables) {
		this.positionables = positionables;
	}

	public Polygon getBorder() {
		return border;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public Round getActiveRound() {
		return activeRound;
	}

	public void setActiveRound(Round activeRound) {
		this.activeRound = activeRound;
	}
	
	public void addRound(Round round){
		rounds.add(round);
	}
	
	public void deleteRound(Round round){
		rounds.remove(round);
	}

	public void setBorder(Polygon border) {
		this.border = border;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Quest) {
			Quest quest = (Quest) obj;
			if (quest.getId().equals(this.getId())) {
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
}
