package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.ZoomToFitEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class ZoomToFitPanel extends SidePanel {

   public ZoomToFitPanel() {
      super("Zoom","Verplaats de kaart zo dat alle spelden in het beeld passen.",true);

      this.getElement().setId("walltoggle");
      setHorizontalAlignment(ALIGN_CENTER);
      add(createZoomToFit());
   }
   
   private Button createZoomToFit() {
      Button button = new Button("Fit", new ClickHandler() {

         @Override
         public void onClick(ClickEvent arg0) {
            ZoomToFitEvent event=new ZoomToFitEvent();
            EventBus.get().fireEvent(event);
         }
      
      });
      return button;
   }

}
