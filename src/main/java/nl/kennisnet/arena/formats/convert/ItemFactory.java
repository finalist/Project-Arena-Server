package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.Item;
import nl.kennisnet.arena.model.Type;

public class ItemFactory {

	public static Item getInstance(final Type positionable, final String submitUrl){		
		return new Item(positionable, submitUrl);
	}
}
