package nl.kennisnet.arena.formats.convert;

import org.springframework.stereotype.Component;

import nl.kennisnet.arena.formats.Item;
import nl.kennisnet.arena.model.ContentElement;

@Component
public class ItemFactory {

	public Item getInstance(final ContentElement positionable, final String submitUrl){		
		return new Item(positionable, submitUrl);
	}
}
