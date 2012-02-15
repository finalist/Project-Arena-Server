package nl.kennisnet.arena.client;

import nl.kennisnet.arena.client.panel.ButtonPanel;
import nl.kennisnet.arena.client.panel.MapPanel;
import nl.kennisnet.arena.client.panel.QuestDetailsPanel;
import nl.kennisnet.arena.client.panel.QuestItemsPanel;
import nl.kennisnet.arena.client.panel.ResizablePanel;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ArenaDesigner extends HorizontalPanel implements ResizablePanel {

   private ButtonPanel leftPanel;
   private MapPanel mapPanel;
   private QuestDetailsPanel questDetailsPanel;
   private QuestItemsPanel itemPanel;

   public ArenaDesigner() {
      leftPanel = new ButtonPanel();
      mapPanel = new MapPanel();
      questDetailsPanel = new QuestDetailsPanel(false);
      itemPanel = new QuestItemsPanel(false);

      add(leftPanel);
      add(mapPanel);
      add(createRightPanel());
   }

   private Panel createRightPanel() {
      VerticalPanel result = new VerticalPanel();
      result.getElement().setPropertyInt("cellSpacing", 3);
      result.add(questDetailsPanel);
      result.add(itemPanel);
      return result;
   }
   
   public void zoomMap(){
      mapPanel.zoomToFit();
   }

   @Override
   public void resize(int x, int y) {
      // leftPanel.resize(150, y);
      itemPanel.resize(300, y - 200);
      mapPanel.resize(x - 450, y);
   }

}
