package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.FeatureTxt;
import nl.kennisnet.arena.formats.Location;
import nl.kennisnet.arena.model.Information;

import com.google.common.base.Function;

public class InformationConvertor implements Function<Information, FeatureTxt> {
	@Override
	public FeatureTxt apply(Information i) {
		Location convert = new Location(i.getLocation().getPoint().getX(), i.getLocation().getPoint().getY());
		FeatureTxt informationFeature = new FeatureTxt("feature_" + i.getId().toString(), i.getText(), convert);

		return informationFeature;
	}
}
