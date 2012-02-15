package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UpdateQuestItemEvent extends GwtEvent<UpdateQuestItemEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onUpdateQuestItem(UpdateQuestItemEvent p);
   }

   @Override
   protected void dispatch(UpdateQuestItemEvent.Handler handler) {
      handler.onUpdateQuestItem(this);
   }

   @Override
   public GwtEvent.Type<UpdateQuestItemEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<UpdateQuestItemEvent.Handler> TYPE = new GwtEvent.Type<UpdateQuestItemEvent.Handler>();

   
}
