package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.widget.ExtendedTextBox;
import nl.kennisnet.arena.client.widget.FormTablePanel;

public class StoryQuestItemDialog extends QuestItemDialog {

   private ExtendedTextBox storyTextBox; 
   
   private static int MAX_CHARACTERS=160;

   public StoryQuestItemDialog(QuestItemDTO itemDTO,boolean readOnlyDialog,boolean create) {
      super(itemDTO,readOnlyDialog,create);
      setText("Verhaal details");
   }



   @Override
   protected List<FormTablePanel.Element> createFormPanels(){
      List<FormTablePanel.Element> result=new ArrayList<FormTablePanel.Element>();
      result.add(createNamePanel());
      result.add(createStoryPanel("Verhaal"));
      result.add(createRadiusPanel());
      result.add(createVisibleRadiusPanel());
      return result;
   }
   
   protected FormTablePanel.Element createStoryPanel(String title){
      storyTextBox=new ExtendedTextBox(80,4,MAX_CHARACTERS);
      return new FormTablePanel.Element(title,storyTextBox);
   }
   
   protected void fillFormFromItem(QuestItemDTO itemDTO){
      super.fillFormFromItem(itemDTO);
      storyTextBox.setText(itemDTO.getDescription());
   }
   
   
   protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO){
      QuestItemDTO result=super.fillItemFromForm(itemDTO);
      result.setDescription(storyTextBox.getText());
      return result;
   }
   
}
