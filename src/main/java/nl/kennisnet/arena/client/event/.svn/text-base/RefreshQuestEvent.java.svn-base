package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RefreshQuestEvent extends GwtEvent<RefreshQuestEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onRefreshQuest(RefreshQuestEvent p);
   }

   @Override
   protected void dispatch(RefreshQuestEvent.Handler handler) {
      handler.onRefreshQuest(this);
   }

   @Override
   public GwtEvent.Type<RefreshQuestEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<RefreshQuestEvent.Handler> TYPE = new GwtEvent.Type<RefreshQuestEvent.Handler>();

   
}
