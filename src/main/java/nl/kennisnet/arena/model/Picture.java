package nl.kennisnet.arena.model;

import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/** Used for image upload */
@Entity
public class Picture implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String contentType;

	@Lob
	@Basic(fetch = javax.persistence.FetchType.LAZY)
	private byte[] content;

	public void setContent(byte[] newContent) {
		if (newContent == null) {
			this.content = new byte[0];
		} else {
			this.content = Arrays.copyOf(newContent, newContent.length);
		}
	}

	public byte[] getContent() {
		return content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
}
