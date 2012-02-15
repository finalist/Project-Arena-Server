package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ItemFilterEvent extends GwtEvent<ItemFilterEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onItemFilter(ItemFilterEvent p);
   }

   @Override
   protected void dispatch(ItemFilterEvent.Handler handler) {
      handler.onItemFilter(this);
   }

   @Override
   public GwtEvent.Type<ItemFilterEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<ItemFilterEvent.Handler> TYPE = new GwtEvent.Type<ItemFilterEvent.Handler>();

   
}
