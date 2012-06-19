package nl.kennisnet.arena.formats.convert;

import java.util.List;

import nl.kennisnet.arena.formats.Arena;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.utils.ArenaDataBean;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.configuration.CompositeConfiguration;

public class ArenaFactory {

	public static Arena getInstance(final ArenaDataBean data, final CompositeConfiguration configuration) {
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		
		Arena arena = new Arena();
		List<Positionable> positionables = data.getQuest().getVisiblePositionables(data.getLocation());
		
		arena.addPositionResults(positionables, baseUrl, data);
		arena.checkStats();
		
		return arena;
	}
	
	public static Arena getOfflineInstance(final ArenaDataBean data, final CompositeConfiguration configuration) {
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		
		Arena arena = new Arena();
		List<Positionable> positionables = data.getQuest().getPositionables();
		
		arena.addPositionResults(positionables, baseUrl, data);
		arena.checkStats();
		
		return arena;
	}
	
}
