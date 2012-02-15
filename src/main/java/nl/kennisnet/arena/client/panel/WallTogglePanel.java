package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.WallToggleEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

public class WallTogglePanel extends SidePanel implements RefreshQuestEvent.Handler {
   
   private final ToggleButton wallButton;
   
   public WallTogglePanel() {
      super("Omheining","Voorkom verdwalen met een virtuele omheining.",true);
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);

      this.getElement().setId("walltoggle");
      HorizontalPanel togglePanel=new HorizontalPanel();
      togglePanel.setHorizontalAlignment(ALIGN_LEFT);
      wallButton=createWallButton();      
      togglePanel.setHorizontalAlignment(ALIGN_CENTER);
      togglePanel.add(wallButton);
      togglePanel.setWidth("100%");
      add(togglePanel);
   }

   
   private ToggleButton createWallButton() {
      ToggleButton result = new ToggleButton(new Image("switchoff.png"),new Image("switchon.png"));
      result.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent arg0) {
            WallToggleEvent wallToggleEvent = new WallToggleEvent();
            wallToggleEvent.setWallUp(wallButton.isDown());
            EventBus.get().fireEvent(wallToggleEvent);
         }
      });
      return result;
   }

   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      if (QuestState.getInstance().getState().getBorder() != null&&!wallButton.isDown()) {
         wallButton.setDown(true);
      }
      
   }

}
