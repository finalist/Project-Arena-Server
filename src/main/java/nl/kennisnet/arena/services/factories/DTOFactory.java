package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.client.elements.ImageElement;
import nl.kennisnet.arena.client.elements.QuestionElement;
import nl.kennisnet.arena.client.elements.StoryElement;
import nl.kennisnet.arena.client.elements.VideoElement;
import nl.kennisnet.arena.model.ContentElement;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.model.Video;

public class DTOFactory {

public static QuestDTO create(Quest quest) {
		if (quest == null) {
			return null;
		}
		QuestDTO result = new QuestDTO();
		result.setId(quest.getId());
		result.setName(quest.getName());
		result.setEmailOwner(quest.getEmailOwner());
		if (quest.getLocations() != null) {
			ArrayList<PoiDTO> items = new ArrayList<PoiDTO>();
			for (Location location : quest.getLocations()) {
				items.add(create(location));
			}
			result.setItems(items);
		}
		result.setBorder(GeomUtil.createSimplePolygon(quest.getBorder()));
		if (quest.getActiveRound() != null) {
			result.setActiveRound(new RoundDTO(quest.getActiveRound().getId(),
					quest.getActiveRound().getName()));
		}
		result.setRounds(roundsToRoundDto(quest.getRounds()));
		return result;
	}

	public static PoiDTO create(Location location) {
		PoiDTO result = null;
		if (location != null) {
			result = new PoiDTO();
			result.setName(location.getName());
			
			for(ContentElement t : location.getElements()) {
				if(t instanceof Information) {
					StoryElement element = new StoryElement(((Information) t).getText());
					result.getElements().add(element);
				} else if (t instanceof Image) {
					ImageElement element = new ImageElement(((Image) t).getUrl());
					result.getElements().add(element);
				} else if ( t instanceof Question) {
					QuestionElement element = new QuestionElement();
					element.setDescription(((Question) t).getText());
					element.setOption1(((Question) t).getAnswer1());
					element.setOption2(((Question) t).getAnswer2());
					element.setOption3(((Question) t).getAnswer3());
					element.setOption4(((Question) t).getAnswer4());
					element.setQuestionType(((Question) t).getQuestionType());
					element.setCorrectOption(((Question) t).getCorrectAnswer());
					result.getElements().add(element);
				} else if (t instanceof Video) {
					VideoElement element = new VideoElement(((Video) t).getVideoUrl());
					result.getElements().add(element);
				} else if (t instanceof Object3D){
				    //TODO:Add element
				}
			}
			if (result != null) {
				result.setRadius(location.getRadius());
				result.setVisibleRadius(location.getVisibleRadius());
				result.setPoint(GeomUtil.createSimplePoint(location.getPoint()));
				result.setId(location.getId());
			}
		}

		return result;
	}

	public static ActionDTO create(ParticipationLog log) {
		ActionDTO action = new ActionDTO();
		action.setAction(log.getAction());
		if (log.getAnswer() != null) {
			action.setAnswer(log.getAnswer());
		}
		action.setPoint(GeomUtil.createSimplePoint(log.getLocation()));
		action.setTeamName(log.getParticipation().getParticipant().getName());
		action.setQuestId(log.getParticipation().getQuest().getId());
		action.setTimeInMillis(log.getTime().getTime());
		if (log.getElement() != null) {
			action.setPositionableId(log.getElement().getId());
		}
		return action;
	}

	public static TeamDTO create(Participation participation) {
		TeamDTO team = new TeamDTO();
		team.setName(participation.getParticipant().getName());
		team.setScore(participation.getScore());
		team.setColor(participation.getParticipant().getHexColor());
		team.setId(participation.getParticipant().getId());
		return team;
	}

	public static TeamDTO create(Participant participant) {
		TeamDTO team = new TeamDTO();
		team.setName(participant.getName());
		team.setColor(participant.getHexColor());
		team.setId(participant.getId());
		return team;
	}

	private static List<RoundDTO> roundsToRoundDto(List<Round> rounds) {
		List<RoundDTO> result = new ArrayList<RoundDTO>();
		for (Round round : rounds) {
			RoundDTO roundDTO = new RoundDTO(round.getId(), round.getName());
			result.add(roundDTO);
		}
		return result;
	}

}
