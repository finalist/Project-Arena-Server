package nl.kennisnet.arena.client.event;

import nl.kennisnet.arena.client.domain.QuestItemDTO;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SelectQuestItemEvent extends GwtEvent<SelectQuestItemEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onSelectQuestItem(SelectQuestItemEvent p);
   }

   @Override
   protected void dispatch(SelectQuestItemEvent.Handler handler) {
      handler.onSelectQuestItem(this);
   }

   @Override
   public GwtEvent.Type<SelectQuestItemEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<SelectQuestItemEvent.Handler> TYPE = new GwtEvent.Type<SelectQuestItemEvent.Handler>();

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
