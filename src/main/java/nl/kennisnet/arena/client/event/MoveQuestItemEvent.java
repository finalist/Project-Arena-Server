package nl.kennisnet.arena.client.event;

import nl.kennisnet.arena.client.domain.QuestItemDTO;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MoveQuestItemEvent extends GwtEvent<MoveQuestItemEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onMoveQuestItem(MoveQuestItemEvent p);
   }

   @Override
   protected void dispatch(MoveQuestItemEvent.Handler handler) {
      handler.onMoveQuestItem(this);
   }

   @Override
   public GwtEvent.Type<MoveQuestItemEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<MoveQuestItemEvent.Handler> TYPE = new GwtEvent.Type<MoveQuestItemEvent.Handler>();

   /**
    * Custom data held within this event object.
    */
   private QuestItemDTO questItem;

   public QuestItemDTO getQuestItem() {
      return questItem;
   }

   public void setQuestItem(QuestItemDTO questItem) {
      this.questItem = questItem;
   }

   
   
}
