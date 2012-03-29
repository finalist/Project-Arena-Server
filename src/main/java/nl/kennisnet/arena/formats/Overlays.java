package nl.kennisnet.arena.formats;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Overlays {

	private List<OverlayTxt> textOverlays;
	
	private List<OverlayImg> imageOverlays;

	public Overlays() {
		
	}

	public Overlays(List<OverlayTxt> textOverlays, List<OverlayImg> imageOverlays) {
		super();
		this.textOverlays = textOverlays;
		this.imageOverlays = imageOverlays;
	}

	public List<OverlayTxt> getTextOverlays() {
		return textOverlays;
	}

	public List<OverlayImg> getImageOverlays() {
		return imageOverlays;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}		
}
