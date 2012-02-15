package nl.kennisnet.arena.formats.general;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public abstract class OverlayAbstract extends PositionableAbstract {

	/**
	 * General overlay properties
	 */
	
	@XmlAttribute
	public abstract String getId();
	
	@XmlElement
	public abstract double getX();
	
	@XmlElement
	public abstract double getY();
	
	@XmlElement
	public abstract String getAnchor();

	@XmlElement
	public abstract String getOnPress();
	
	/**
	 * Overlay-specific properties
	 */

	@XmlElement
	public String getAssetId() {
		return null;
	}
	
	@XmlElement
	public String getText() {
		return null;
	}
	
	@XmlElement
	public Double getWidth() {
		return null;
	}
}
