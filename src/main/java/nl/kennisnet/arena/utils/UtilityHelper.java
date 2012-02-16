package nl.kennisnet.arena.utils;

import nl.kennisnet.arena.model.Quest;

import org.apache.commons.configuration.CompositeConfiguration;

/**
 * Utility helper for methods that are called often and needed in
 * several different places.
 * @author pvink
 *
 */
public final class UtilityHelper {

	private UtilityHelper(){}
	
	/**
	 * Build a server url to use with gamaray
	 * @param quest
	 * @param name
	 * @param configuration
	 * @return
	 */
	public static String url(final Quest quest, final String name, CompositeConfiguration configuration) {
		return String.format("%smixare/%d.gddf?player=%s", getBaseUrl(configuration), quest.getId(), name);
	}

	public static String getBaseUrl(CompositeConfiguration configuration) {
		final String url = configuration.getString("application.baseUrl");
		return url.endsWith("/") ? url : url+"/";
	}
}
