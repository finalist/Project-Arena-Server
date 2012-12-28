package nl.kennisnet.arena.client.elements;

public class StoryElement extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;

	public StoryElement() {
		super();
	}
	public StoryElement(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
