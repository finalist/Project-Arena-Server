package nl.kennisnet.arena.formats;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement
public class Dimension {
	private static final String DIMENSION_VERSION = "1.0";
	@XmlAttribute
	private String version;
	@XmlElement
	private String name;
	@XmlElement
	private boolean relativeAltitude = true;
	@XmlElement
	private String refreshUrl;
	@XmlElement
	private RefreshTime refreshTime;
	@XmlElement
	private RefreshDistance refreshDistance;
	private Features features;
	
	@XmlElementWrapper(name = "assets")
	@XmlElement(name = "asset")	
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

	@XmlElement
	public Features getFeatures() {
		return features;
	}
	
	public List<Asset> getAssets() {
		return assets;
	}
	
	@XmlElement
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
