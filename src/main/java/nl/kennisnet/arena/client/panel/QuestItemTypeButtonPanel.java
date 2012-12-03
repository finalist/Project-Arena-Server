package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.QuestItemTypes;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.widget.SingleSelectList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class QuestItemTypeButtonPanel extends SidePanel{

   private SingleSelectList tools;

   public QuestItemTypeButtonPanel() {
      super("Plaatsen","Prik een speld op de kaart.",true);
      this.getElement().setId("typeselect");
      tools = new SingleSelectList();
      tools.addOption(QuestItemTypes.QUEST_TYPE_STORY, "Verhaal");
      tools.addOption(QuestItemTypes.QUEST_TYPE_QUESTION, "Vraag");
      tools.addOption(QuestItemTypes.QUEST_TYPE_PHOTO, "Foto");
      tools.addOption(QuestItemTypes.QUEST_TYPE_VIDEO, "Video");
      tools.addOption(QuestItemTypes.QUEST_TYPE_3D, "Object3D");
      tools.addOption(QuestItemTypes.QUEST_TYPE_COMBI, "Combined");

      tools.addValueChangeHandler(new ValueChangeHandler<Object>() {
         
         @Override
         public void onValueChange(ValueChangeEvent<Object> event) {
            if (tools.getValue()!=null){
               QuestState.getInstance().setSelectedQuestType((String)tools.getValue());
            }
         }
      });
      
      tools.setValue(QuestItemTypes.QUEST_TYPE_STORY,false);
      add(tools);
   }

}
