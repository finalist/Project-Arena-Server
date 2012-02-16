package nl.kennisnet.arena.formats;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Dimension {
	private static final String DIMENSION_VERSION = "1.0";
	
	private String version;
	
	private String name;
	
	private boolean relativeAltitude = true;
	
	private String refreshUrl;

	
	private RefreshTime refreshTime;
	
	private RefreshDistance refreshDistance;
	private Features features;
	
	private List<Asset> assets;
	
	private Overlays overlays;

	public Dimension(String name, 
			String refreshUrl, 
			List<FeatureTxt> featuresTxt, 
			List<FeatureImg> featuresImg, 
			List<Asset> assets, 
			List <OverlayTxt> overlaysTxt, 
			List <OverlayImg> overlaysImg) {
		
		this.version = DIMENSION_VERSION;
		this.name = name;
		this.refreshUrl = refreshUrl;
		this.refreshTime = new RefreshTime(30000, true);
		this.refreshDistance = new RefreshDistance(300);
		
		Features features = new Features(featuresTxt, featuresImg);
		this.features = features;
		
		this.assets = assets;
		
		Overlays overlays = new Overlays(overlaysTxt, overlaysImg);
		this.overlays = overlays;
	}

	public Dimension() {
	}
	
	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public boolean isRelativeAltitude() {
		return relativeAltitude;
	}
	
	public String getRefreshUrl() {
		return refreshUrl;
	}
	
	public RefreshTime getRefreshTime() {
		return refreshTime;
	}
	
	public RefreshDistance getRefreshDistance() {
		return refreshDistance;
	}

	public Features getFeatures() {
		return features;
	}
	
	public List<Asset> getAssets() {
		return assets;
	}
	
	public Overlays getOverlays() {
		return overlays;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   Dimension other = (Dimension) obj;
		   return new EqualsBuilder()
		                 .appendSuper(super.equals(obj))
		                 .append(name, other.getName())
		                 .isEquals();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
