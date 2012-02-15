package nl.kennisnet.arena.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;

public class ToggleLabel extends Label {
   
   private boolean down;
   private String color;
   
   
   public ToggleLabel(String text,String color){
      super(text);
      this.addClickHandler(new ClickHandler() {
         
         @Override
         public void onClick(ClickEvent arg0) {
            setDown(!down);
            
         }
      });
      updateStyle();
   }

   private void updateStyle(){
      if (down){
         DOM.setStyleAttribute(this.getElement(), "background", color); 
      } else {
         DOM.setStyleAttribute(this.getElement(), "background", "#fffff"); 
      }
   }
   
   public boolean isDown() {
      return down;
   }

   public void setDown(boolean down) {
      this.down = down;
      updateStyle();
   }
   
   
   
   

}
