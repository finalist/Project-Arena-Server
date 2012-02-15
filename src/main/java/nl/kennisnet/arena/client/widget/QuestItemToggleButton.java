package nl.kennisnet.arena.client.widget;

import nl.kennisnet.arena.client.domain.QuestItemDTO;

import com.google.gwt.maps.client.overlay.Icon;

public class QuestItemToggleButton extends ExtendedToggleButton {

   private QuestItemDTO item;

   public QuestItemToggleButton(QuestItemDTO item,Icon icon) {
      super(item.getName(), null, icon.getImageURL());
      this.item = item;
   }

   public QuestItemDTO getQuestItem() {
      return item;
   }
}
