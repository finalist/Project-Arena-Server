package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.model.Video;

public class DomainObjectFactory {
	
	public static Quest create(QuestDTO questDTO) {
		Quest result = new Quest();
		// result.setId(questDTO.getId());
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
		} else if (questItemDTO.getTypeName().equalsIgnoreCase("Object3D")) {
			result = new Object3D();
			((Object3D) result).setUrl(questItemDTO.getObjectURL());
			((Object3D) result)
					.setBlended((questItemDTO.getBlended() == 0) ? false : true);
			;
			((Object3D) result).setSchaal(questItemDTO.getSchaal());
			((Object3D) result).setRotation(questItemDTO.getRotation());

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
	
	private static List<Positionable> positionablesToDelete(List<Positionable> posList, List<QuestItemDTO> itemDTOList) {
		List<Positionable> removeList = new ArrayList<Positionable>();
		for (Positionable pos : posList) {
			boolean exist = false;
			for (QuestItemDTO item : itemDTOList) {
				if (pos.getId().equals(item.getId())) {
					exist = true;
				}
			}
			if (!exist) {
				removeList.add(pos);
			}
		}
		return removeList;
	}
	
	public static Quest update(QuestDTO questDTO, Quest originalQuest) {
		Quest quest = originalQuest;
		quest.setName(questDTO.getName());
		
		for (Positionable pos : positionablesToDelete(quest.getPositionables(), questDTO.getItems())) {
			quest.getPositionables().remove(pos);
		}
		
		if (questDTO.getItems() != null) {
			for (QuestItemDTO questItemDTO : questDTO.getItems()) {
				if (questItemDTO.getId() != null) {
					System.out.println(">>>>>>>>>>>>>>>>>>> DTO ID: " + questItemDTO.getId());
					for (Positionable found : quest.getPositionables()) {
						System.out.println("FOUND ID: " + found.getId());
						if (found.getId().equals(questItemDTO.getId())) {
							System.out.println("GEVONDEN");
							updatePositionable(found, questItemDTO);
						}
					}
				} else {
					Positionable positionable = create(questItemDTO, quest);
					positionable.setQuest(quest);
					quest.getPositionables().add(positionable);
				}
			}
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
	
	private static void updatePositionable(Positionable positionable, QuestItemDTO questItemDTO) {
		if (positionable instanceof Information) {
			((Information) positionable).setName(questItemDTO.getName());
			((Information) positionable).setText((questItemDTO.getDescription()));
		} else if (positionable instanceof Question) {
			((Question) positionable).setText(questItemDTO.getDescription());
			((Question) positionable).setAnswer1(questItemDTO.getOption1());
			((Question) positionable).setAnswer2(questItemDTO.getOption2());
			((Question) positionable).setAnswer3(questItemDTO.getOption3());
			((Question) positionable).setAnswer4(questItemDTO.getOption4());
			((Question) positionable).setQuestionType((questItemDTO.getQuestionType()));
			((Question) positionable).setCorrectAnswer(questItemDTO.getCorrectOption());
		} else if (positionable instanceof Image) {
			((Image) positionable).setUrl(questItemDTO.getObjectURL());
		} else if (positionable instanceof Video) {
			((Video) positionable).setVideoUrl(questItemDTO.getObjectURL());
		} else if (positionable instanceof Object3D) {
			((Object3D) positionable).setUrl(questItemDTO.getObjectURL());
		}
		
		positionable.setName(questItemDTO.getName());
		Location location = new Location(
				GeomUtil.createJTSPoint(questItemDTO.getPoint()),
				questItemDTO.getAlt(), questItemDTO.getRadius(),
				questItemDTO.getVisibleRadius());
		positionable.setLocation(location);
	}

	public static List<Positionable> delete(Quest quest, Quest originalQuest) {
		List<Positionable> result = new ArrayList<Positionable>();
		for (Positionable originalPos : originalQuest.getPositionables()) {
			if (!quest.getPositionables().contains(originalPos)) {
				result.add(originalPos);
			}
		}
		return result;
	}

}
