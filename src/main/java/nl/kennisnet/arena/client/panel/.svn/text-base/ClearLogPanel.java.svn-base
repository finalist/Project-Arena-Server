package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class ClearLogPanel extends SidePanel {

   public ClearLogPanel() {
      super("Wissen","Wis de gebruikersgegevens van deze arena zodat teams opnieuw kunnen starten",true);

      this.getElement().setId("clearlog");
      setHorizontalAlignment(ALIGN_CENTER);
      add(createClearLog());
   }
   
   private Button createClearLog() {
      Button button = new Button("Wis", new ClickHandler() {

         @Override
         public void onClick(ClickEvent arg0) {
            GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
            questService.clearLog(QuestState.getInstance().getState().getId(), new AsyncCallback<LogDTO>() {
               
               @Override
               public void onSuccess(LogDTO arg0) {
                  QuestState.getInstance().setLog(arg0);
                  EventBus.get().fireEvent(new RefreshQuestLogEvent());
               }
               
               @Override
               public void onFailure(Throwable arg0) {
                  Window.alert("Het wissen van de monitor gegevens van de Arena is mislukt.");
               }
            });
         }
      
      });
      return button;
   }

}
