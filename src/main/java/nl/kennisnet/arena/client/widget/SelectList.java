package nl.kennisnet.arena.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class SelectList extends UnsortedList implements HasChangeHandlers, HasValue<Object> {

   private List<Option> options;
   private boolean valueChangeHandlerInitialized = false;

   public SelectList() {
      super();
      // this.getElement().setId(id);
      options = new ArrayList<Option>();
   }

   public void addOption(Object id, String text) {
      Option newOption = new Option(id, text);
      options.add(newOption);
      this.add(newOption.getListItem());
   }

   protected List<Option> getOptions() {
      return options;
   }

   protected abstract void clickOption(Object id);

   @Override
   public void setValue(Object value, boolean fireEvents) {
      setValue(value);
      if (fireEvents) {
         ValueChangeEvent.fire(SelectList.this, value);
      }
   }

   @Override
   public HandlerRegistration addChangeHandler(ChangeHandler handler) {
      return addDomHandler(handler, ChangeEvent.getType());
   }

   @Override
   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Object> handler) {
      if (!valueChangeHandlerInitialized) {
         valueChangeHandlerInitialized = true;
         addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
               ValueChangeEvent.fire(SelectList.this, getValue());
            }
         });
      }
      return addHandler(handler, ValueChangeEvent.getType());
   }

   public String getOptionName(Object key) {
      for (Option option : options) {
         if (option.getId().equals(key)) {
            return option.getListItem().getText();
         }
      }
      return null;
   }

   public void updateOptionName(Object key, String newName) {
      for (Option option : options) {
         if (option.getId().equals(key)) {
            option.getListItem().setText(newName);
         }
      }
   }

   public boolean isOptionSelected(Object key) {
      for (Option option : options) {
         if (option.getId().equals(key)) {
            return option.isSelected();
         }
      }
      return false;
   }

   public void selectOption(Object key) {
      for (Option option : options) {
         if (option.getId().equals(key)) {
            option.select();
         }
      }
   }

   
   
   protected class Option {
      private final Object id;
      private final ListItem item;
      private boolean selected;

      public Option(Object id, String text) {
         this.id = id;
         item = new ListItem(text,id.toString(), new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
               clickOption(getId());
            }
         });
      }

      public Object getId() {
         return id;
      }

      public ListItem getListItem() {
         return item;
      }

      public boolean isSelected() {
         return selected;
      }

      public void deselect() {
         item.setStyleName(null);
         selected = false;
      }

      public void select() {
         item.setStyleName("active");
         selected = true;
      }
   }

}
