package nl.kennisnet.arena.client.panel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemTypes;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.CreateQuestItemEvent;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.ItemFilterEvent;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.widget.MultiSelectList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class TypeFilterPanel extends SidePanel implements RefreshQuestEvent.Handler, CreateQuestItemEvent.Handler {

   private final MultiSelectList filter;

   
   public TypeFilterPanel() {
      super("Toon","Toon spelden van de soort",true);

      filter = new MultiSelectList();
      filter.addOption(QuestItemTypes.QUEST_TYPE_STORY, "Verhaal (0)");
      filter.addOption(QuestItemTypes.QUEST_TYPE_QUESTION, "Vraag (0)");
      filter.addOption(QuestItemTypes.QUEST_TYPE_PHOTO, "Foto (0)");
      filter.addOption(QuestItemTypes.QUEST_TYPE_VIDEO, "Video (0)");
      
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
      EventBus.get().addHandler(CreateQuestItemEvent.TYPE, this);

      add(createFilterPanel());

      synchPanelFromFilter();
      updateTypeCounters();
   }
   
   private Panel createFilterPanel(){
      SimplePanel filterPanel=new SimplePanel();
      
      filterPanel.getElement().setId("typefilter");


      filter.addValueChangeHandler(new ValueChangeHandler<Object>() {
         
         @Override
         public void onValueChange(ValueChangeEvent<Object> event) {
            if (filter.getValue()!=null){
               synchFilterFromPanel();
               EventBus.get().fireEvent(new ItemFilterEvent());
            }
         }
      });
      filterPanel.add(filter);
      return filterPanel;
   }

   private void synchFilterFromPanel(){
      List<String> filterValues=(List<String>)filter.getValue();
      for (String typeId : QuestItemTypes.getTypes()) {
         if (QuestState.getInstance().isTypeVisible(typeId)!=filterValues.contains(typeId)){
            QuestState.getInstance().toggleTypeVisible(typeId);
         }
      }
   }

   private void synchPanelFromFilter(){
      List<String> filterValues=(List<String>)filter.getValue();
      for (String typeId : QuestItemTypes.getTypes()) {
         if (QuestState.getInstance().isTypeVisible(typeId)&&filterValues.contains(typeId)){
            filterValues.remove(typeId);
         } else if (QuestState.getInstance().isTypeVisible(typeId)&&!filterValues.contains(typeId)){
            filterValues.add(typeId);
         }
      }
      filter.setValue(filterValues, false);
   }

   
   private void updateTypeCounters(){
      Map<String,Integer> counter=new HashMap<String, Integer>();
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (questDTO != null && questDTO.getItems() != null) {
         for (QuestItemDTO itemDTO : QuestState.getInstance().getState().getItems()) {
            if (counter.get(itemDTO.getTypeName())==null){
               counter.put(itemDTO.getTypeName(),1);
            } else {
               Integer newValue=counter.get(itemDTO.getTypeName())+1;
               counter.put(itemDTO.getTypeName(),newValue);
            }
         }
      }
      for (String typeId : QuestItemTypes.getTypes()) {
         int typeCount=0;
         if (counter.get(typeId)!=null){
            typeCount=counter.get(typeId);
         }
         String oldName=filter.getOptionName(typeId);
         String newName=oldName.replaceFirst("\\((\\d)+\\)", "("+typeCount+")");
         filter.updateOptionName(typeId,newName);
      }
      
   }
   
   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      updateTypeCounters();
   }

   @Override
   public void onCreateQuestItem(CreateQuestItemEvent p) {
      if (!filter.isOptionSelected(p.getQuestItem().getTypeName())){
         filter.enable(p.getQuestItem().getTypeName());
      }
      updateTypeCounters();
   }

}
