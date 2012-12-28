package nl.kennisnet.arena.client.widget;

import nl.kennisnet.arena.client.domain.PoiDTO;

import com.google.maps.gwt.client.MarkerImage;

public class QuestItemToggleButton extends ExtendedToggleButton {

   private PoiDTO item;

   public QuestItemToggleButton(PoiDTO item,MarkerImage icon) {
      super(item.getName(), null, icon.getUrl());
      this.item = item;
   }

   public PoiDTO getQuestItem() {
      return item;
   }
}
