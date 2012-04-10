package nl.kennisnet.arena.client.widget;

import nl.kennisnet.arena.client.domain.QuestItemDTO;

import com.google.maps.gwt.client.MarkerImage;

public class QuestItemToggleButton extends ExtendedToggleButton {

   private QuestItemDTO item;

   public QuestItemToggleButton(QuestItemDTO item,MarkerImage icon) {
      super(item.getName(), null, icon.getUrl());
      this.item = item;
   }

   public QuestItemDTO getQuestItem() {
      return item;
   }
}
