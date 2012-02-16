package nl.kennisnet.arena.formats;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

public class RefreshTime {

	private int validFor;
	private boolean waitForAssets = true;
	
	public RefreshTime() {
		
	}
	
	public RefreshTime(int validFor, boolean waitForAssets) {
		this.validFor = validFor;
		this.waitForAssets = waitForAssets;
	}

	public int getValidFor() {
		return validFor;
	}
	
	public boolean getWaitForAssets() {
		return waitForAssets;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
