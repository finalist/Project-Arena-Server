package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RefreshQuestLogEvent extends GwtEvent<RefreshQuestLogEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onRefreshQuestLog(RefreshQuestLogEvent p);
   }

   @Override
   protected void dispatch(RefreshQuestLogEvent.Handler handler) {
      handler.onRefreshQuestLog(this);
   }

   @Override
   public GwtEvent.Type<RefreshQuestLogEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<RefreshQuestLogEvent.Handler> TYPE = new GwtEvent.Type<RefreshQuestLogEvent.Handler>();

   
}
