package nl.kennisnet.arena.formats;

import org.apache.commons.lang.builder.ToStringBuilder;

public class RefreshDistance {

	private int validWithinRange;

	public RefreshDistance() {
		
	}
	
	public RefreshDistance(int validWithinRange) {
		super();
		this.validWithinRange = validWithinRange;
	}

	public int getValidWithinRange() {
		return validWithinRange;
	}

	public void setValidWithinRange(int validWithinRange) {
		this.validWithinRange = validWithinRange;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
