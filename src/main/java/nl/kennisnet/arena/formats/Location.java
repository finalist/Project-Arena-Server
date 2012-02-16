package nl.kennisnet.arena.formats;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Location {
	private double lon;
	private double lat;
	public Location(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	public Location() {
	   super();
	}
	
	public double getLon() {
		return lon;
	}
	
	public double getLat() {
		return lat;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(lon).append(lat).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   Location other = (Location) obj;
		   return new EqualsBuilder()
		                 .appendSuper(super.equals(obj))
		                 .append(lon, other.getLon())
		                 .append(lat, other.getLat())
		                 .isEquals();
	}	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
