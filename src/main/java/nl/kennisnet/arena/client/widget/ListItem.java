package nl.kennisnet.arena.client.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasClickHandlers {

   private Element text;
   

   public ListItem(String text,String id,ClickHandler clickHandler) {
      Element liElement=DOM.createElement("LI");
      this.text=DOM.createAnchor();
      this.text.setInnerText(text);
      this.text.setId(id);
      liElement.appendChild(this.text);
      setElement(liElement);
      addClickHandler(clickHandler);
   }
   
   public void add(Widget w) {
      super.add(w, getElement());
   }

   public void insert(Widget w, int beforeIndex) {
      super.insert(w, getElement(), beforeIndex, true);
   }

   public String getText() {
      return text.getInnerText();
   }
   
   public void setText(String text){
      this.text.setInnerText(text);
   }

   @Override
   public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addDomHandler(handler, ClickEvent.getType());
   }

   
}
