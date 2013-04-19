package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.Item;
import nl.kennisnet.arena.model.ContentElement;

public class ItemFactory {

	public static Item getInstance(final ContentElement positionable, final String submitUrl){		
		return new Item(positionable, submitUrl);
	}
}
