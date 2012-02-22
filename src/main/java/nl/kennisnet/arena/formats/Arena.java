package nl.kennisnet.arena.formats;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.utils.UtilityHelper;

public class Arena {

	private String stats;

	private enum STATS {
		OK, NO_OBJECTS
	}

	private List<Result> results = new ArrayList<Result>();

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public void checkStats() {
		if (results.size() > 0) {
			stats = STATS.OK.toString();
		} else {
			stats = STATS.NO_OBJECTS.toString();
		}
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public void addResult(Result result) {
		results.add(result);
	}

	public void addPositionResults(List<Positionable> positionables, String baseUrl, long questId,
			String player, long participationId,  ParticipantService participantService) {
		
		for (Positionable positionable : positionables) {
			Result result = new Result();
			result.setId(positionable.getId());
			result.setLat((float) positionable.getLocation().getPoint().getY());
			result.setLng((float) positionable.getLocation().getPoint().getX());
			result.setTitle(positionable.getName());
			result.setHasDetailPage(true);
			result.setWebpage(baseUrl + "item/show/" + questId + "/"
					+ positionable.getId() + "/" + player
					+ ".item");
			result.setObjectType(getPositionableType(positionable));
			buildQuestionImage(baseUrl, positionable, result,
					participationId, participantService);
			results.add(result);
		}
		
	}

	private String getPositionableType(Positionable positionable) {
		if (positionable instanceof Image) {
			return "image";
		} else if (positionable instanceof Information) {
			return "information";
		} else if (positionable instanceof Question) {
			return "question";
		}
		return "";
	}
	
	private void buildQuestionImage(String baseUrl, Positionable positionable, Result result, long participationId, ParticipantService participantService) {
		if (positionable instanceof Question) {
			ParticipantAnswer participantAnswer = participantService.getParticipationAnswer(participationId, (Question)positionable);
			String objectUrl = "";
			if(participantAnswer == null){
				objectUrl = baseUrl + "images/blue-question.png";
			}
			if(participantAnswer != null){
				if(participantAnswer.getAnswer().equals(((Question)positionable).getCorrectAnswer())){
					objectUrl = baseUrl + "images/green-question.png";
				}
				else{
					objectUrl = baseUrl + "images/red-question.png";
				}				
			}
			result.setObjectUrl(objectUrl);
		}
		return;
	}

	public int getNumResults() {
		return results.size();
	}

	@Override
	public String toString() {
		String value = "{   " + surroundStringWithQuotations("stats") + ":"
				+ surroundStringWithQuotations(getStats()) + ","
				+ surroundStringWithQuotations("num_results") + ":"
				+ surroundStringWithQuotations(getNumResults()) + ",";

		value += surroundStringWithQuotations("results") + ": [";
		for (Result result : results) {
			value += result.toString() + ",";
		}
		// remove the last comma
		value = value.substring(0, value.length() - 1);
		value += "] }";
		return value;
	}

	private String surroundStringWithQuotations(String value) {
		return "\"" + value + "\"";
	}

	private String surroundStringWithQuotations(int value) {
		return "\"" + value + "\"";
	}
}
