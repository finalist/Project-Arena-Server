package nl.kennisnet.arena.client.dialog;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemTypes;

public class DialogSelector {

	public static void showRelevantDialog(String questType, QuestItemDTO itemDTO,boolean readOnly,boolean create) {
      if (questType.equals(QuestItemTypes.QUEST_TYPE_STORY)) {
         new StoryQuestItemDialog(itemDTO, readOnly,create).center();
      } else if (questType.equals(QuestItemTypes.QUEST_TYPE_QUESTION)) {
         new QuestionQuestItemDialog(itemDTO, readOnly,create).center();
      } else if (questType.equals(QuestItemTypes.QUEST_TYPE_PHOTO)) {
         new ImageQuestItemDialog(itemDTO, readOnly,create).center();
      } else if(questType.equals(QuestItemTypes.QUEST_TYPE_VIDEO)) {
    	 new VideoQuestItemDialog(itemDTO, readOnly, create).center();    	  
      } else if(questType.equals(QuestItemTypes.QUEST_TYPE_3D)) {
    	  new Object3DDialog(itemDTO, readOnly, create).center();
      } else if(questType.equals(QuestItemTypes.QUEST_TYPE_COMBI)) {
     	 new CombiDialog(itemDTO, readOnly, create).center();    	     
      } else {
         new QuestItemDialog(itemDTO, readOnly,create).center();
      }

   }
}
