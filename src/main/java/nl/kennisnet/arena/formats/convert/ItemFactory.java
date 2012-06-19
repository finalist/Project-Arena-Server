package nl.kennisnet.arena.formats.convert;

import nl.kennisnet.arena.formats.Item;
import nl.kennisnet.arena.model.Positionable;

public class ItemFactory {

	public static Item getInstance(final Positionable positionable, final String submitUrl){		
		return new Item(positionable, submitUrl);
	}
}
