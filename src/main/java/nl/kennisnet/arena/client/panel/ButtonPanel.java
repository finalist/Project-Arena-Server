package nl.kennisnet.arena.client.panel;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ButtonPanel extends VerticalPanel implements ResizablePanel {

   public ButtonPanel() {
      super();
      getElement().setPropertyInt("cellSpacing", 3);
      add(new QuestItemTypeButtonPanel());
      add(new TypeFilterPanel());
      add(new WallTogglePanel());
      add(new ZoomToFitPanel());
   }



   @Override
   public void resize(int x, int y) {
      setHeight(y+"px");
      setWidth(x+"px");
   }

}
