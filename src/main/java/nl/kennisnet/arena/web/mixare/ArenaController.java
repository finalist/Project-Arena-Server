package nl.kennisnet.arena.web.mixare;

import java.util.Date;

import nl.kennisnet.arena.formats.Arena;
import nl.kennisnet.arena.formats.convert.ArenaFactory;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.services.QuestService;
import nl.kennisnet.arena.services.factories.GeomUtil;
import nl.kennisnet.arena.utils.ArenaDataBean;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/mixare")
public class ArenaController {

	private final QuestService questService;
	private final ParticipantService participantService;
	private final CompositeConfiguration configuration;
	
	private Logger log = Logger.getLogger(ArenaController.class);
	
	private final String playerParam = "player";
	private final String latitudeParam = "lat";
	private final String longitudeParam = "lng";
	
	
	@Autowired
	public ArenaController(final QuestService questService, final ParticipantService participantService, final CompositeConfiguration configuration) {
		this.questService = questService;
		this.participantService = participantService;
		this.configuration = configuration;
	}

	/**
	 * This method will be called by the mobile application mixare, and asks for the json data matching its
	 * latitude and longitude.
	 * @return The browser will return json data according to the lat & lng parameters
	 */
	@RequestMapping(value = "/{questId}", method = RequestMethod.GET, params = {playerParam, latitudeParam, longitudeParam}) @ResponseBody
	public String mixareJsonRequest(@PathVariable Long questId, @RequestParam(playerParam) final String player,
								@RequestParam(latitudeParam) final float latitude, @RequestParam(longitudeParam) final float longitude){
			
		final long participantId = participantService.getParticipantId(player);
		final Quest quest = questService.getQuest(questId);
		final long participationId = questService.participateInQuest(participantId, quest);

		final ArenaDataBean data = new ArenaDataBean(questId, player, latitude, longitude, participantId, quest, null, participationId);
		data.setParticipantService(participantService);

		String pLog = "player: "+ player + " on lat: "+ latitude+ " and lng "+ longitude;
		participantService.addParticipationLog(participationId, new Date().getTime(), pLog
		, GeomUtil.createJTSPoint(latitude, longitude));		
		
		log.debug("default get: [quest=" + questId + ",player=" + player + "]");		
		if(quest != null){	
			final Arena arena = ArenaFactory.getInstance(data, configuration);

			log.debug("response model: " + arena);
			Gson gson = new Gson();
			return gson.toJson(arena);		
		}else{
			return "error, quest not found";
		}
	}
	
	/**
	 * This method will be called by the mobile application mixare, and asks for all json data in that quest (arena)
	 * @return The browser returns offline data to the application
	 */
	@RequestMapping(value = "/{questId}", method = RequestMethod.GET, params = {playerParam}) @ResponseBody
	public String mixareOfflineJsonRequest(@PathVariable Long questId, @RequestParam(playerParam) final String player){
			
		final long participantId = participantService.getParticipantId(player);
		final Quest quest = questService.getQuest(questId);
		final long participationId = questService.participateInQuest(participantId, quest);

		final ArenaDataBean data = new ArenaDataBean(questId, player, participantId, quest, null, participationId);
		data.setParticipantService(participantService);

		log.debug("default get: [quest=" + questId + ",player=" + player + " OFFLINE mode]");		
		if(quest != null){	
			final Arena arena = ArenaFactory.getOfflineInstance(data, configuration);

			log.debug("response model: " + arena);
			Gson gson = new Gson();
			return gson.toJson(arena);		
		}else{
			return "error, quest not found";
		}
	}
	
	
	/**
	 * This method will run if no parameters are send with the url. Mixare will first run this
	 * url to check if the page exists.
	 * @param questId
	 * @return
	 */
	@RequestMapping(value = "/{questId}", method = RequestMethod.GET) @ResponseBody
	public String mixareCallback(@PathVariable Long questId){
		return "Please specify the team(player), latitude(lat) and longitude(lng)";		
	}
}
