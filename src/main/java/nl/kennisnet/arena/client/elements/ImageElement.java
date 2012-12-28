package nl.kennisnet.arena.client.elements;

public class ImageElement extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String link;

	public ImageElement() {
		super();
	}
	
	public ImageElement(String link) {
		super();
		this.link = link;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
