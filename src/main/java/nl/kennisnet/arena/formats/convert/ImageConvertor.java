package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.FeatureImg;
import nl.kennisnet.arena.formats.Location;
import nl.kennisnet.arena.model.Image;

import com.google.common.base.Function;

public class ImageConvertor implements Function<Image, FeatureImg> {

	@Override
	public FeatureImg apply(Image t) {
		Location location = new Location(t.getLocation().getPoint().getX(), t.getLocation().getPoint().getY());
		return new FeatureImg(String.format("image_%d", t.getId()), String.format("asset_%d", t.getId()), location);
	}

}
