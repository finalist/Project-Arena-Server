package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TeamFilterEvent extends GwtEvent<TeamFilterEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onTeamFilter(TeamFilterEvent p);
   }

   @Override
   protected void dispatch(TeamFilterEvent.Handler handler) {
      handler.onTeamFilter(this);
   }

   @Override
   public GwtEvent.Type<TeamFilterEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<TeamFilterEvent.Handler> TYPE = new GwtEvent.Type<TeamFilterEvent.Handler>();

   
}
