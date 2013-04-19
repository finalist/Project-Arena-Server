package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.elements.Element;
import nl.kennisnet.arena.client.elements.ImageElement;
import nl.kennisnet.arena.client.elements.QuestionElement;
import nl.kennisnet.arena.client.elements.StoryElement;
import nl.kennisnet.arena.client.elements.VideoElement;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Round;
import nl.kennisnet.arena.model.Video;

public class DomainObjectFactory {

    public static Quest create(QuestDTO questDTO) {
        Quest result = new Quest();
        result.setName(questDTO.getName());
        result.setEmailOwner(questDTO.getEmailOwner());
        if (questDTO.getItems() != null) {
            List<Location> items = new ArrayList<Location>();
            for (PoiDTO PoiDTO : questDTO.getItems()) {
                Location location = create(PoiDTO, result);
                if (location != null) {
                    items.add(location);
                }

            }
            result.setLocations(items);
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

    private static Location create(PoiDTO PoiDTO, Quest quest) {
        Location result = new Location(GeomUtil.createJTSPoint(PoiDTO
                .getPoint()), PoiDTO.getAlt(), PoiDTO.getRadius(),
                PoiDTO.getVisibleRadius());
        result.setName(PoiDTO.getName());
        for (Element e : PoiDTO.getElements()) {
            if (e instanceof ImageElement) {
                Image img = new Image();
                img.setLocation(result);
                img.setUrl(((ImageElement) e).getLink());
                result.getElements().add(img);
            } else if (e instanceof StoryElement) {
                Information info = new Information();
                info.setLocation(result);
                info.setText(((StoryElement) e).getDescription());
                result.getElements().add(info);
            } else if (e instanceof QuestionElement) {
                Question q = new Question(
                        ((QuestionElement) e).getDescription(),
                        ((QuestionElement) e).getOption1(),
                        ((QuestionElement) e).getOption2(),
                        ((QuestionElement) e).getOption3(),
                        ((QuestionElement) e).getOption4(),
                        ((QuestionElement) e).getQuestionType());
                q.setCorrectAnswer(((QuestionElement) e).getCorrectOption());
                q.setLocation(result);
                result.getElements().add(q);

            } else if (e instanceof VideoElement) {
                Video vid = new Video();
                vid.setLocation(result);
                vid.setVideoUrl(((VideoElement) e).getUrl());
                result.getElements().add(vid);
            }

        }
        result.setQuest(quest);
        return result;
    }

    private static List<Location> locationsToDelete(List<Location> locList, List<PoiDTO> itemDTOList) {
        List<Location> removeList = new ArrayList<Location>();
        for (Location location : locList) {
            boolean exist = false;
            for (PoiDTO item : itemDTOList) {
                if (location.getId().equals(item.getId())) {
                    exist = true;
                }
            }
            if (!exist) {
                removeList.add(location);
            }
        }
        return removeList;
    }

    public static Quest update(QuestDTO questDTO, Quest originalQuest) {
        Quest quest = originalQuest;
        quest.setName(questDTO.getName());

        for (Location location : locationsToDelete(quest.getLocations(), questDTO.getItems())) {
            quest.getLocations().remove(location);
        }

        if (questDTO.getItems() != null) {
            for (PoiDTO PoiDTO : questDTO.getItems()) {
                if (PoiDTO.getId() != null) {
                    Location foundLocation = findLocation(quest, PoiDTO.getId());
                    if (foundLocation != null) {
                        updateLocation(foundLocation, PoiDTO);
                    } else {
                        // TODO:throw exception
                    }
                } else {
                    Location createdLocation = create(PoiDTO, quest);
                    createdLocation.setQuest(quest);
                    quest.getLocations().add(createdLocation);
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

    private static Location findLocation(Quest quest, Long locationId) {
        for (Location location : quest.getLocations()) {
            return location;
        }
        return null;
    }

    private static void updateLocation(Location location, PoiDTO PoiDTO) {
        // TODO:

        // if (positionable instanceof Information) {
        // ((Information) positionable).setName(PoiDTO.getName());
        // ((Information) positionable).setText((PoiDTO.getDescription()));
        // } else if (positionable instanceof Question) {
        // ((Question) positionable).setText(PoiDTO.getDescription());
        // ((Question) positionable).setAnswer1(PoiDTO.getOption1());
        // ((Question) positionable).setAnswer2(PoiDTO.getOption2());
        // ((Question) positionable).setAnswer3(PoiDTO.getOption3());
        // ((Question) positionable).setAnswer4(PoiDTO.getOption4());
        // ((Question) positionable)
        // .setQuestionType((PoiDTO.getQuestionType()));
        // ((Question) positionable).setCorrectAnswer(PoiDTO
        // .getCorrectOption());
        // } else if (positionable instanceof Image) {
        // ((Image) positionable).setUrl(PoiDTO.getObjectURL());
        // } else if (positionable instanceof Video) {
        // ((Video) positionable).setVideoUrl(PoiDTO.getObjectURL());
        // } else if (positionable instanceof Object3D) {
        // ((Object3D) positionable).setUrl(PoiDTO.getObjectURL());
        // }
        // } else if (positionable instanceof Combined) {
        // ((Combined) positionable).setItems(PoiDTO.getItems());
        // }

        location.setPoint(GeomUtil.createJTSPoint(PoiDTO.getPoint()));
        location.setAlt(PoiDTO.getAlt());
        location.setRadius(PoiDTO.getRadius());
        location.setVisibleRadius(PoiDTO.getVisibleRadius());
        location.setName(PoiDTO.getName());
    }


}
