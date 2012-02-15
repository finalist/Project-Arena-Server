package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ZoomToFitEvent extends GwtEvent<ZoomToFitEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onZoomToFit(ZoomToFitEvent p);
   }

   @Override
   protected void dispatch(ZoomToFitEvent.Handler handler) {
      handler.onZoomToFit(this);
   }

   @Override
   public GwtEvent.Type<ZoomToFitEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<ZoomToFitEvent.Handler> TYPE = new GwtEvent.Type<ZoomToFitEvent.Handler>();

   
}
