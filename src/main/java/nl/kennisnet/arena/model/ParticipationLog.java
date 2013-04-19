package nl.kennisnet.arena.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
public class ParticipationLog implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Participation participation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	private String action;
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Point location;

	@ManyToOne
	private ContentElement element;
	private String answer;

	public ParticipationLog() {
		super();
	}

	public ParticipationLog(final Participation participation, final Date time, final String action, final Point location) {
		super();
		this.participation = participation;
		this.time = time;
		this.action = action;
		this.location = location;
	}

	public ParticipationLog(final Participation participation, final Date time, final String action, final Point location, final ContentElement element) {
		super();
		this.participation = participation;
		this.time = time;
		this.action = action;
		this.location = location;
		this.element = element;
	}

	public ParticipationLog(final Participation participation, final Date time, final String action, final Point location, final ContentElement element,
			final String answer) {
		super();
		this.participation = participation;
		this.time = time;
		this.action = action;
		this.location = location;
		this.element = element;
		this.answer = answer;
	}

	public Long getId() {
		return id;
	}

	public Participation getParticipation() {
		return participation;
	}

	public Date getTime() {
		return time;
	}

	public String getAction() {
		return action;
	}

	public ContentElement getElement() {
		return element;
	}
	
	public void setElement(ContentElement element) {
		this.element = element;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
