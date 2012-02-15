package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.FeatureTxt;
import nl.kennisnet.arena.formats.Location;
import nl.kennisnet.arena.model.Positionable;

import com.google.common.base.Function;

public class PositionableRadarConvertor implements Function<Positionable, FeatureTxt> {
	@Override
	public FeatureTxt apply(Positionable i) {
		Location convert = new Location(i.getLocation().getPoint().getX(), i.getLocation().getPoint().getY());
		FeatureTxt informationFeature = new FeatureTxt("feature_" + i.getId().toString(), i.getName(), convert);

		return informationFeature;
	}
}
