package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.model.Video;

public class DomainObjectFactory {

	public static Quest create(QuestDTO questDTO) {
		Quest result = new Quest();
		result.setId(questDTO.getId());
		result.setName(questDTO.getName());
		result.setEmailOwner(questDTO.getEmailOwner());
		if (questDTO.getItems() != null) {
			List<Positionable> items = new ArrayList<Positionable>();
			for (QuestItemDTO questItemDTO : questDTO.getItems()) {
				Positionable positionable = create(questItemDTO, result);
				if (positionable != null) {
					items.add(positionable);
				}
			}
			result.setPositionables(items);
		}

		result.setBorder(GeomUtil.createJTSPolygon(questDTO.getBorder()));
		result.getRounds().clear();
		for (RoundDTO round : questDTO.getRounds()) {
			result.addRound(new Round(round.getId(), round.getName(), result));
		}
		if (result.getRounds().size() > 0) {
			result.setActiveRound(result.getRounds().get(0));
		}
		return result;
	}

	private static Positionable create(QuestItemDTO questItemDTO, Quest quest) {
		Positionable result = null;
		if (questItemDTO.getTypeName().equals("Verhaal")) {
			result = new Information(questItemDTO.getName(),
					questItemDTO.getDescription());
		} else if (questItemDTO.getTypeName().equals("Vraag")) {
			Question question = new Question(questItemDTO.getDescription(),
					questItemDTO.getOption1(), questItemDTO.getOption2(),
					questItemDTO.getOption3(), questItemDTO.getOption4(),
					questItemDTO.getQuestionType());
			question.setCorrectAnswer(questItemDTO.getCorrectOption());
			result = question;
		} else if (questItemDTO.getTypeName().equals("Foto")) {
			result = new Image();
			((Image) result).setUrl(questItemDTO.getObjectURL());
		} else if (questItemDTO.getTypeName().equals("Video")) {
			result = new Video();
			((Video) result).setVideoUrl(questItemDTO.getObjectURL());
		}
		if (result != null) {
			result.setName(questItemDTO.getName());
			Location location = new Location(
					GeomUtil.createJTSPoint(questItemDTO.getPoint()),
					questItemDTO.getAlt(), questItemDTO.getRadius(),
					questItemDTO.getVisibleRadius());
			result.setLocation(location);
		}
		result.setQuest(quest);
		return result;
	}

	public static Quest update(QuestDTO questDTO, Quest quest) {
		quest.setName(questDTO.getName());
		quest.setEmailOwner(questDTO.getEmailOwner());

		if (questDTO.getItems() != null) {
			List<Positionable> items = new ArrayList<Positionable>();
			for (QuestItemDTO questItemDTO : questDTO.getItems()) {
				if (questItemDTO.getId() != null) {
					// TODO:Update
					
				} else {
					Positionable positionable = create(questItemDTO, quest);
					positionable.setQuest(quest);
					items.add(positionable);
				}

			}
			quest.setPositionables(items);
		}
		quest.setBorder(GeomUtil.createJTSPolygon(questDTO.getBorder()));
		RoundDTO activeRoundDTO = questDTO.getActiveRound();
		if (activeRoundDTO != null) {
			quest.setActiveRound(new Round(activeRoundDTO.getId(),
					activeRoundDTO.getName(), quest));
		}
		quest.getRounds().clear();
		for (RoundDTO round : questDTO.getRounds()) {
			Round r = new Round(round.getId(), round.getName(), quest);
			quest.addRound(r);
		}
		return quest;
	}

	public static List<Positionable> delete(Quest quest, Quest originalQuest) {
		List<Positionable> result = new ArrayList<Positionable>();
		for (Positionable orriginalPos : originalQuest.getPositionables()) {
			if (!quest.getPositionables().contains(orriginalPos)) {
				result.add(orriginalPos);
			}
		}
		return result;
	}

}
