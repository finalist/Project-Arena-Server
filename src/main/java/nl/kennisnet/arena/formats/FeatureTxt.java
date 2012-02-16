package nl.kennisnet.arena.formats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FeatureTxt {

	private String id;
	private String text;
	private String anchor;
	private String onPress;
	private int xLoc;
	private int yLoc;
	private int zLoc;
	private boolean showInRadar = true;
	private Location location;
	
	public FeatureTxt(String id, String text, Location location) {
		this.id = id;
		this.text = text;
		this.location = location;
	}

	public FeatureTxt() {
	   super();
	}
		
	public String getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}

	public String getAnchor() {
		return anchor;
	}

	public String getOnPress() {
		return onPress;
	}

	public int getxLoc() {
		return xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public int getzLoc() {
		return zLoc;
	}

	public boolean isShowInRadar() {
		return showInRadar;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   FeatureTxt other = (FeatureTxt) obj;
		   return new EqualsBuilder()
		                 .appendSuper(super.equals(obj))
		                 .append(id, other.getId())
		                 .isEquals();
	}
		
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
