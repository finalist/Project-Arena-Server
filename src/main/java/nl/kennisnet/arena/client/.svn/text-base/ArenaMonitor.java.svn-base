package nl.kennisnet.arena.client;

import nl.kennisnet.arena.client.panel.ClearLogPanel;
import nl.kennisnet.arena.client.panel.MonitorMapPanel;
import nl.kennisnet.arena.client.panel.QuestDetailsPanel;
import nl.kennisnet.arena.client.panel.QuestItemsPanel;
import nl.kennisnet.arena.client.panel.ResizablePanel;
import nl.kennisnet.arena.client.panel.TeamFilterPanel;
import nl.kennisnet.arena.client.panel.ZoomToFitPanel;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ArenaMonitor extends HorizontalPanel implements ResizablePanel {

   private Panel contentPanel;
   private MonitorMapPanel monitorMapPanel;
   private QuestDetailsPanel questDetailsPanel;
   private QuestItemsPanel questItemsPanel;

   public ArenaMonitor() {
      Panel panel = new HorizontalPanel();
      panel.add(createLeftPanel());
      contentPanel = new SimplePanel();
      panel.add(contentPanel);
      panel.add(createRightPanel());
      this.add(panel);
      monitorMapPanel = new MonitorMapPanel();

      refreshContent();
   }

   private Panel createLeftPanel() {
      VerticalPanel panel = new VerticalPanel();
      panel.getElement().setPropertyInt("cellSpacing", 3);
      panel.add(new TeamFilterPanel());
      panel.add(new ZoomToFitPanel());
      panel.add(new ClearLogPanel());
      return panel;

   }

   private void refreshContent() {
      contentPanel.clear();
      contentPanel.add(monitorMapPanel);
   }

   private Panel createRightPanel() {
      VerticalPanel result = new VerticalPanel();
      result.getElement().setPropertyInt("cellSpacing", 3);
      questDetailsPanel = new QuestDetailsPanel(true);
      questItemsPanel = new QuestItemsPanel(true);
      result.add(questDetailsPanel);
      result.add(questItemsPanel);
      return result;
   }

   @Override
   public void resize(int x, int y) {
      monitorMapPanel.resize(x - 450, y);
      questItemsPanel.resize(300, y - 200);

   }
}
