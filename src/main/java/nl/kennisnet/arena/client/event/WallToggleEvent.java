package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WallToggleEvent extends GwtEvent<WallToggleEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onWallToggle(WallToggleEvent p);
   }

   @Override
   protected void dispatch(WallToggleEvent.Handler handler) {
      handler.onWallToggle(this);
   }

   @Override
   public GwtEvent.Type<WallToggleEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<WallToggleEvent.Handler> TYPE = new GwtEvent.Type<WallToggleEvent.Handler>();

   private Boolean wallUp;

   public Boolean getWallUp() {
      return wallUp;
   }

   public void setWallUp(Boolean wallUp) {
      this.wallUp = wallUp;
   }


   
   
   
}
