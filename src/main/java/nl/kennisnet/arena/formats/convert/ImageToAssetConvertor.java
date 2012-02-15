package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.Asset;
import nl.kennisnet.arena.model.Image;

import com.google.common.base.Function;

public class ImageToAssetConvertor implements Function<Image, Asset> {
	
	@Override
	public Asset apply(Image t) {
		String extension = extensionFor(t);
		Asset asset = new Asset(String.format("asset_%d", t.getId()), extension,  t.getUrl().trim());
		return asset;
	}

	private String extensionFor(Image t) {
		String url = t.getUrl().trim();
		return url.substring(t.getUrl().lastIndexOf(".")+1, t.getUrl().length()).toUpperCase();
	}
}
