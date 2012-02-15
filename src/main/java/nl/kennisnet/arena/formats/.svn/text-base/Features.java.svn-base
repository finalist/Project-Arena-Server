package nl.kennisnet.arena.formats;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Features {

	@XmlElement(name = "featureTxt")
	private List<FeatureTxt> textFeatures;
	@XmlElement(name = "featureImg")
	private List<FeatureImg> imageFeatures;

	public Features() {

	}

	public Features(List<FeatureTxt> textFeatures, List<FeatureImg> imageFeatures) {
		super();
		this.textFeatures = textFeatures;
		this.imageFeatures = imageFeatures;
	}

	public List<FeatureTxt> getTextFeatures() {
		return textFeatures;
	}

	public List<FeatureImg> getImageFeatures() {
		return imageFeatures;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
