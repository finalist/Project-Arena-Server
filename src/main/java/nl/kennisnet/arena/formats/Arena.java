package nl.kennisnet.arena.formats;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Question.TYPE;
import nl.kennisnet.arena.model.Video;
import nl.kennisnet.arena.services.factories.GeomUtil;
import nl.kennisnet.arena.utils.ArenaDataBean;

import com.google.gson.annotations.SerializedName;

public class Arena {

	@SerializedName("stats")
	private String stats;

	@SuppressWarnings("unused")
	@SerializedName("num_results")
	private int numResults = 0;

	private enum STATS {
		OK, NO_OBJECTS
	}

	@SerializedName("results")
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

	public int getNumResults() {
		return results.size();
	}

	public void addPositionResults(List<Positionable> positionables,
			String baseUrl, ArenaDataBean data) {

		for (Positionable positionable : positionables) {
			Result result = new Result();
			result = buildResultData(result, positionable);
			result = buildWebPage(result, positionable, baseUrl, data);
			result = buildPositionableType(result, positionable);
			result = buildObjectImage(result, positionable, baseUrl, data);
			result = buildDistance(result, positionable, data);
			results.add(result);
			numResults++;
		}
	}

	private Result buildResultData(Result result, Positionable positionable) {
		result.setId(positionable.getId());
		result.setLat((float) positionable.getLocation().getPoint().getY());
		result.setLng((float) positionable.getLocation().getPoint().getX());
		result.setTitle(positionable.getName());
		return result;
	}

	private Result buildWebPage(Result result, Positionable positionable,
			String baseUrl, ArenaDataBean data) {
		if (webPageNeeded(data, positionable)) {
			result.setHasDetailPage(true);
			if (data.isOffline()) {
				result.setWebpage(baseUrl + "item/offline/show/"
						+ data.getQuestId() + "/" + positionable.getId() + "/"
						+ data.getPlayer() + ".item");
			} else if (positionable instanceof Question) {
				result.setWebpage(baseUrl + "item/show/" + data.getQuestId()
						+ "/" + positionable.getId() + "/" + data.getPlayer()
						+ ".item");
			} else if (positionable instanceof Information) {
				result.setWebpage(baseUrl + "item/show/" + positionable.getId()
						+ ".item");
			} else if (positionable instanceof Image) {
				result.setWebpage(((Image) positionable).getUrl());
			} else if (positionable instanceof Video) {
				result.setWebpage(((Video) positionable).getVideoUrl());
			}
//			} else if (positionable instanceof Object3D) {
//				result.setWebpage(((Object3D) positionable).getUrl());
//			}
		} else {
			result.setHasDetailPage(false);
		}
		return result;
	}

	private Result buildPositionableType(Result result,
			Positionable positionable) {
		if (positionable instanceof Image) {
			result.setObjectType("image");
		} else if (positionable instanceof Information) {
			result.setObjectType("information");
		} else if (positionable instanceof Question) {
			result.setObjectType("question");
		} else if (positionable instanceof Video) {
			result.setObjectType("video");
		} else if (positionable instanceof Object3D) {
			result.setObjectType("object3d");
		}
		return result;
	}

	private Result buildObjectImage(Result result, Positionable positionable,
			String baseUrl, ArenaDataBean data) {
		if (!webPageNeeded(data, positionable)) {
			result.setObjectUrl(baseUrl + "images/too-far.png");
			result.setTitle("Onbekend (afstand te groot)");
		} else if (positionable instanceof Question) {

			ParticipantAnswer participantAnswer = data.getParticipantService()
					.getParticipationAnswer(data.getParticipationId(),
							(Question) positionable);

			result.setObjectUrl(buildQuestionImage(positionable,
					participantAnswer, baseUrl));

		} else if (positionable instanceof Information) {
			result.setObjectUrl(buildInformationImage(baseUrl));
		} else if (positionable instanceof Image) {
			result.setObjectUrl(buildPhotoImage(positionable, baseUrl, data));
		} else if (positionable instanceof Video) {
			result.setObjectUrl(((Video) positionable).getThumbnailUrl());
		} else if (positionable instanceof Object3D) {
			result.setObjectUrl(buildInformationImage(baseUrl));
		}
		return result;
	}

	private String buildQuestionImage(Positionable positionable,
			ParticipantAnswer participantAnswer, String baseUrl) {
		String objectUrl = "";
		Question question = (Question) positionable;
		if (question.getQuestionTypeAsEnum() == TYPE.MULTIPLE_CHOICE) {
			if (participantAnswer == null) {
				objectUrl = baseUrl + "images/blue-question.png";
			}
			if (participantAnswer != null) {
				if (participantAnswer.getAnswer().equals(
						((Question) positionable).getCorrectAnswer())) {
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

	private String buildPhotoImage(Positionable positionable, String baseUrl,
			ArenaDataBean data) {
		String url = data.getParticipantService().getImageUrl(
				(positionable).getId());
		return url;
	}

	private Result buildDistance(Result result, Positionable positionable,
			ArenaDataBean data) {
		if (data.isOffline()) {
			result.setRadius(positionable.getLocation().getRadius());
		}
		return result;
	}

	private boolean webPageNeeded(ArenaDataBean data, Positionable positionable) {
		if (positionable.getLocation().getVisibleRadius() == null
				|| GeomUtil.calculateDistanceInMeters(data.getLocation(),
						positionable.getLocation().getPoint()) < positionable
						.getLocation().getVisibleRadius()) {
			return true;
		}
		if (data.isOffline()) { // offline mode activated
			return true;
		}
		return false;
	}
}
