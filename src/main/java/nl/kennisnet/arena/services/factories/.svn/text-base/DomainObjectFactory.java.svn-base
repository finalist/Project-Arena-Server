package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;

public class DomainObjectFactory {

   public static Quest create(QuestDTO questDTO) {
      Quest result = new Quest();
      result.setId(questDTO.getId());
      result.setName(questDTO.getName());
      result.setEmailOwner(questDTO.getEmailOwner());
      if (questDTO.getItems() != null) {
         List<Positionable> items = new ArrayList<Positionable>();
         for (QuestItemDTO questItemDTO : questDTO.getItems()) {
            Positionable positionable = create(questItemDTO);
            if (positionable != null) {
               items.add(positionable);
            }
         }
         result.setPositionables(items);
      }
      
      result.setBorder(GeomUtil.createJTSPolygon(questDTO.getBorder()));
      return result;
   }

   private static Positionable create(QuestItemDTO questItemDTO) {
      Positionable result = null;
      if (questItemDTO.getTypeName().equals("Verhaal")) {
         result = new Information(questItemDTO.getName(), questItemDTO.getDescription());
      } else if (questItemDTO.getTypeName().equals("Vraag")) {
         Question question = new Question(questItemDTO.getDescription(),questItemDTO.getOption1(),questItemDTO.getOption2(),questItemDTO.getOption3(),questItemDTO.getOption4());
         question.setCorrectAnswer(questItemDTO.getCorrectOption());
         result=question;
      } else if (questItemDTO.getTypeName().equals("Foto")) {
         result = new Image();
         ((Image)result).setUrl(questItemDTO.getObjectURL());
      }
      if (result!=null){
         result.setName(questItemDTO.getName());
         Location location = new Location(GeomUtil.createJTSPoint(questItemDTO.getPoint()), questItemDTO.getAlt(), questItemDTO.getRadius());
         result.setLocation(location);
      }
      
      return result;
   }



   
}


