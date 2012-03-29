package nl.kennisnet.arena.formats;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Features {

	private List<FeatureTxt> textFeatures;
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
