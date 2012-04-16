package nl.kennisnet.arena.client.dialog;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SaveQuestDialog extends DialogBox {

   private TextBox nameTextBox = new TextBox();
   private TextBox emailTextBox = new TextBox();
   
   // private LatLng point;

   public SaveQuestDialog() {
      super(true, true);
      setText("Opslaan als");
      fillFormFromItem();
      setWidget(createPanel());
      nameTextBox.setFocus(true);
   }

   protected Panel createPanel(){
      VerticalPanel panel = new VerticalPanel();
      panel.add(createNamePanel());
      panel.add(createEmailPanel());
      panel.add(createButtonPanel());
      return panel;
   }

   private Panel createButtonPanel() {
      HorizontalPanel buttonPanel=new HorizontalPanel();
      Button saveButton = new Button("Bewaar", new ClickHandler() {
         @Override
         public void onClick(ClickEvent arg0) {
            fillItemFromForm();
            GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
            questService.save(QuestState.getInstance().getState(), true, new AsyncCallback<QuestDTO>() {
               @Override
               public void onSuccess(QuestDTO arg0) {
//                  com.google.gwt.user.client.Window.alert("Saving of Areana succeded!");
                  QuestState.getInstance().setState(arg0);
                  EventBus.get().fireEvent(new RefreshQuestEvent());
                  hide();
               }

               @Override
               public void onFailure(Throwable arg0) {
                  Window.alert(arg0.getMessage());
                  com.google.gwt.user.client.Window.alert("Er heeft zich een fout voorgedaan bij het opslaan van de Arena :"+arg0.getMessage());
                  hide();
               }
            });
         }

      });
      buttonPanel.add(saveButton);
      Button cancelButton = new Button("Annuleren", new ClickHandler() {
         @Override
         public void onClick(ClickEvent arg0) {
            hide();
         }

      });

      buttonPanel.add(cancelButton);
      
      return buttonPanel;
   }
   
   
   
   protected void fillFormFromItem(){
      nameTextBox.setText(QuestState.getInstance().getState().getName().trim());
      emailTextBox.setText(QuestState.getInstance().getState().getEmailOwner().trim());
   }
   
   
   protected void fillItemFromForm(){
      QuestState.getInstance().getState().setName(nameTextBox.getText());
      QuestState.getInstance().getState().setEmailOwner(emailTextBox.getText());
   }
   
   protected Panel createNamePanel(){
      Panel panel=new HorizontalPanel();
      panel.add(new Label("Name"));
      panel.add(nameTextBox);
      return panel;
   }

   protected Panel createEmailPanel(){
      Panel panel=new HorizontalPanel();
      panel.add(new Label("Email"));
      panel.add(emailTextBox);
      return panel;
   }
   
   
}
