package nl.kennisnet.arena.client.event;

import nl.kennisnet.arena.client.domain.QuestItemDTO;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CreateQuestItemEvent extends GwtEvent<CreateQuestItemEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onCreateQuestItem(CreateQuestItemEvent p);
   }

   @Override
   protected void dispatch(CreateQuestItemEvent.Handler handler) {
      handler.onCreateQuestItem(this);
   }

   @Override
   public GwtEvent.Type<CreateQuestItemEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<CreateQuestItemEvent.Handler> TYPE = new GwtEvent.Type<CreateQuestItemEvent.Handler>();

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
