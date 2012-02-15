package nl.kennisnet.arena.client.widget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FormTablePanel extends SimplePanel {

   private final HTMLTable formTable;

   public FormTablePanel() {
      super();
      formTable = new FlexTable();
      add(formTable);
   }

   public void addField(String label, Widget field) {
      int row = formTable.getRowCount();
      formTable.setWidget(row, 0, new Label(label));
      formTable.setWidget(row, 1, field);
   }
   
   public void addField(Element element){
      addField(element.getLabel(),element.getField());
   }

   public static class Element{
      
      private final String label;
      private final Widget field;
      
      public Element(String label,Widget field){
         this.label=label;
         this.field=field;
      }

      public String getLabel() {
         return label;
      }

      public Widget getField() {
         return field;
      }
      
      
      
   }
   
}
