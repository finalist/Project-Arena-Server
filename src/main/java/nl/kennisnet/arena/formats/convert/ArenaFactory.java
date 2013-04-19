package nl.kennisnet.arena.formats.convert;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.formats.Arena;
import nl.kennisnet.arena.formats.Result;
import nl.kennisnet.arena.model.ContentElement;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Video;
import nl.kennisnet.arena.model.Question.TYPE;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.services.factories.GeomUtil;
import nl.kennisnet.arena.utils.ArenaDataBean;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.configuration.CompositeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArenaFactory {

	@Autowired
	private ParticipantService participantService;

	
	public Arena getInstance(final ArenaDataBean data, final CompositeConfiguration configuration) {
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		
		Arena arena = new Arena();
		List<Location> locations = data.getQuest().getVisiblePositionables(data.getLocation());
		
		arena.setResults(createLocationResults(locations, baseUrl, data));
		arena.checkStats();
		
		return arena;
	}
	
	public Arena getOfflineInstance(final ArenaDataBean data, final CompositeConfiguration configuration) {
		String baseUrl = UtilityHelper.getBaseUrl(configuration);
		
		Arena arena = new Arena();
		List<Location> locations = data.getQuest().getLocations();
		
		arena.setResults(createLocationResults(locations, baseUrl, data));
		arena.checkStats();
		
		return arena;
	}
	
	
	private List<Result> createLocationResults(List<Location> locations, String baseUrl,
			ArenaDataBean data) {
		List<Result> results=new ArrayList<Result>();
		
		for (Location location : locations) {
			for (ContentElement element : location.getElements()) {
				Result result = new Result();
				result = buildResultData(result, element);
				result = buildWebPage(result, element, baseUrl, data);
				result = buildPositionableType(result, element);
				result = buildObjectImage(result, element, baseUrl, data);
				result = buildDistance(result, element, data);
				results.add(result);
			}
		}
		return results;
	}

	private Result buildResultData(Result result, ContentElement element) {
		result.setId(element.getId());
		result.setLat((float) element.getLocation().getPoint().getY());
		result.setLng((float) element.getLocation().getPoint().getX());
		result.setTitle(element.getLocation().getName());
		return result;
	}

	private Result buildWebPage(Result result, ContentElement element,
			String baseUrl, ArenaDataBean data) {
		if (webPageNeeded(data, element)) {
			result.setHasDetailPage(true);
			if (data.isOffline()) {
				result.setWebpage(baseUrl + "item/offline/show/"
						+ data.getQuestId() + "/" + element.getId() + "/"
						+ data.getPlayer() + ".item");
			} else if (element instanceof Question) {
				result.setWebpage(baseUrl + "item/show/" + data.getQuestId()
						+ "/" + element.getId() + "/" + data.getPlayer()
						+ ".item");
			} else if (element instanceof Information) {
				result.setWebpage(baseUrl + "item/show/" + element.getId()
						+ ".item");
			} else if (element instanceof Image) {
				result.setWebpage(((Image) element).getUrl());
			} else if (element instanceof Video) {
				result.setWebpage(((Video) element).getVideoUrl());
			} else if (element instanceof Object3D) {
				result.setObjectUrl(((Object3D) element).getUrl());
				result.setRotX(((Object3D) element).getRotation()[0]);
				result.setRotY(((Object3D) element).getRotation()[1]);
				result.setRotZ(((Object3D) element).getRotation()[2]);
			}

		} else {
			result.setHasDetailPage(false);
		}
		return result;
	}

	private Result buildPositionableType(Result result,ContentElement element) {
		if (element instanceof Image) {
			result.setObjectType("image");
		} else if (element instanceof Information) {
			result.setObjectType("information");
		} else if (element instanceof Question) {
			result.setObjectType("question");
		} else if (element instanceof Video) {
			result.setObjectType("video");
		} else if (element instanceof Object3D) {
			result.setObjectType("object3d");
		}
		return result;
	}

	private Result buildObjectImage(Result result, ContentElement element, String baseUrl, ArenaDataBean data) {
		if (!webPageNeeded(data, element)) {
			result.setObjectUrl(baseUrl + "images/too-far.png");
			result.setTitle("Onbekend (afstand te groot)");
		} else if (element instanceof Question) {

			ParticipantAnswer participantAnswer = participantService.getParticipationAnswer(data.getParticipationId(),(Question) element);

			result.setObjectUrl(buildQuestionImage(element, participantAnswer, baseUrl));

		} else if (element instanceof Information) {
			result.setObjectUrl(buildInformationImage(baseUrl));
		} else if (element instanceof Image) {
			result.setObjectUrl(buildPhotoImage(element, baseUrl));
		} else if (element instanceof Video) {
			result.setObjectUrl(((Video) element).getThumbnailUrl());
		}
		return result;
	}

	private String buildQuestionImage(ContentElement element, ParticipantAnswer participantAnswer, String baseUrl) {
		String objectUrl = "";
		Question question = (Question) element;
		if (question.getQuestionTypeAsEnum() == TYPE.MULTIPLE_CHOICE) {
			if (participantAnswer == null) {
				objectUrl = baseUrl + "images/blue-question.png";
			}
			if (participantAnswer != null) {
				if (participantAnswer.getAnswer().equals(
						((Question) element).getCorrectAnswer())) {
					objectUrl = baseUrl + "images/green-question.png";
				} else {
					objectUrl = baseUrl + "images/red-question.png";
				}
			}
		} else if (question.getQuestionTypeAsEnum() == TYPE.OPEN_QUESTION) {
			if (participantAnswer == null) {
				objectUrl = baseUrl + "images/blue-question.png";
			}
			if (participantAnswer != null) {
				objectUrl = baseUrl + "images/green-question.png";
			}
		}
		return objectUrl;
	}

	private String buildInformationImage(String baseUrl) {
		return baseUrl + "images/information.png";
	}

	private String buildPhotoImage(ContentElement element, String baseUrl) {
		return participantService.getImageUrl((element).getId());
	}

	private Result buildDistance(Result result, ContentElement contentElement, ArenaDataBean data) {
		if (data.isOffline()) {
			result.setRadius(contentElement.getLocation().getRadius());
		}
		return result;
	}

	private boolean webPageNeeded(ArenaDataBean data, ContentElement element) {
		if (element.getLocation().getVisibleRadius() == null
				|| GeomUtil.calculateDistanceInMeters(data.getLocation(),
						element.getLocation().getPoint()) < element
						.getLocation().getVisibleRadius()) {
			return true;
		}
		if (data.isOffline()) { // offline mode activated
			return true;
		}
		return false;
	}

	
}
