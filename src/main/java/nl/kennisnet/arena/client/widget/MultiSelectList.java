package nl.kennisnet.arena.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class MultiSelectList extends SelectList {

   public MultiSelectList() {
      super();
   }

   @Override
   protected void clickOption(Object id) {
      for (Option option : getOptions()) {
         if (option.getId().equals(id)) {
            if (option.isSelected()) {
               option.deselect();
            } else {
               option.select();
            }
         }
      }
      ValueChangeEvent.fire(MultiSelectList.this, getValue());
   }

   @Override
   public Object getValue() {
      List<Object> ids = new ArrayList<Object>();
      for (Option option : getOptions()) {
         if (option.isSelected()) {
            ids.add(option.getId());
         }
      }
      return ids;
   }

   @Override
   public void setValue(Object value) {
      List<Object> selectedIds = (List<Object>) value;
      if (selectedIds != null) {
         for (Option option : getOptions()) {
            if (selectedIds.contains(option.getId())) {
               option.select();
            } else {
               option.deselect();
            }
         }
      }

   }

   public void enable(Object id) {
      if (!isOptionSelected(id)) {
         clickOption(id);
      }
   }

}
