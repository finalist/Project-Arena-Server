package nl.kennisnet.arena.formats;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FeatureImg {

	private String id;
	private String anchor;
	private String onPress;
	private int xLoc;
	private int yLoc;
	private int zLoc;
	private boolean showInRadar;
	private String assetId;
	private Location location;
	
	public FeatureImg(String id, String assetId, Location location) {
		this.id = id;
		this.assetId = assetId;
		this.location = location;
	}

	public FeatureImg() {
	   super();
	}
	
	public String getId() {
		return id;
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

	public String getAssetId() {
		return assetId;
	}

	@XmlElement
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
		   FeatureImg other = (FeatureImg) obj;
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
