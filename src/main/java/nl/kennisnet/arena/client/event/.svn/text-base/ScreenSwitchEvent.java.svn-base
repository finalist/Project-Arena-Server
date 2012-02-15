package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ScreenSwitchEvent extends GwtEvent<ScreenSwitchEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onScreenSwitch(ScreenSwitchEvent p);
   }

   @Override
   protected void dispatch(ScreenSwitchEvent.Handler handler) {
      handler.onScreenSwitch(this);
   }

   @Override
   public GwtEvent.Type<ScreenSwitchEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<ScreenSwitchEvent.Handler> TYPE = new GwtEvent.Type<ScreenSwitchEvent.Handler>();

   private final int newView;
   
   public ScreenSwitchEvent(int newView){
      this.newView=newView;
   }
   
   public int getNewView(){
      return this.newView;
   }
   
   
}
