package nl.kennisnet.arena.client.panel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SidePanel extends VerticalPanel {
   
   public SidePanel(String title,String text,boolean leftSide){
      super();
      if (leftSide){
         this.setStyleName("leftSidePanel");
      } else {
         this.setStyleName("rightSidePanel");
      }
      this.add(createStyledLabel(title, "paneltitle"));
      this.add(createStyledLabel(text, "paneltext"));
   }
   
   private Label createStyledLabel(String text,String styleName){
      Label result=new Label(text);
      result.setStyleName(styleName);
      return result;
   }
}
