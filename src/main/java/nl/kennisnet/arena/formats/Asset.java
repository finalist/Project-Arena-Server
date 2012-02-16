package nl.kennisnet.arena.formats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Asset {

	private String id;
	private String format;
	private String url;
	
	public Asset() {
		
	}
	
	public Asset(String id, String format, String url) {
		this.id = id;
		this.format = format;
		this.url = url;
	}
	
	public String getId() {
		return id;
	}

	public String getFormat() {
		return format;
	}

	public String getUrl() {
		return url;
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
		   Asset other = (Asset) obj;
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
