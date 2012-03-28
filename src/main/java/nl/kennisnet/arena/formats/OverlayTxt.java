package nl.kennisnet.arena.formats;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OverlayTxt {

	private String id;
	private double x;
	private double y;
	private String anchor;
	private String onPress;
	private String text;
	private double width;
	
	public OverlayTxt() {
		
	}
		
	public OverlayTxt(String id, double x, double y, String anchor, String onPress,
			String text, double width) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.anchor = anchor;
		this.onPress = onPress;
		this.text = text;
		this.width = width;
	}

	public String getId() {
		return id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getAnchor() {
		return anchor;
	}

	public String getOnPress() {
		return onPress;
	}
	
	public String getText() {
		return text;	
	}
	
	public Double getWidth() {
		return width;
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
		   OverlayTxt other = (OverlayTxt) obj;
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
