package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.dialog.DialogSelector;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.CreateQuestItemEvent;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.ItemFilterEvent;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.SelectQuestItemEvent;
import nl.kennisnet.arena.client.event.UpdateQuestItemEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuestItemsPanel extends SidePanel implements UpdateQuestItemEvent.Handler, CreateQuestItemEvent.Handler,
      RefreshQuestEvent.Handler, ItemFilterEvent.Handler, ResizablePanel {
   
   private boolean readOnly;
   private final Panel itemGrid;
   private final Panel summaryPanel;
   private final Panel scrollPanel;

   public QuestItemsPanel(boolean readOnly) {
      super("Spelden","Hier staat een lijst van de opgegeven en gefilterde spelden",false);
      this.readOnly=readOnly;
      EventBus.get().addHandler(UpdateQuestItemEvent.TYPE, this);
      EventBus.get().addHandler(CreateQuestItemEvent.TYPE, this);
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
      EventBus.get().addHandler(ItemFilterEvent.TYPE, this);
      itemGrid=new VerticalPanel();
      itemGrid.setWidth("100%");
      scrollPanel=new ScrollPanel(itemGrid);
      summaryPanel=new SimplePanel();
      add(scrollPanel);
      setHorizontalAlignment(ALIGN_CENTER);
      add(summaryPanel);
      refresh();
   }

   private Widget createItemWidget(final QuestItemDTO item) {
      FlowPanel result = new FlowPanel();
      result.addStyleName(item.getTypeName().toLowerCase()+"_panel");
      result.addStyleName("questitempanel");

      Label nameLabel = new Label(item.getName());
      nameLabel.addStyleName("questitempanel_name");
      nameLabel.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent arg0) {
            SelectQuestItemEvent eventQI = new SelectQuestItemEvent();
            eventQI.setQuestItem(item);
            EventBus.get().fireEvent(eventQI);
         }
      });

      result.add(nameLabel);
      
      FlowPanel links=new FlowPanel();
      links.addStyleName("itemlinks");
      
      if (!readOnly){
      
         Anchor deleteLabel = new Anchor("Verwijder");
         deleteLabel.setStyleName("deletelink");
         deleteLabel.addClickHandler(new ClickHandler() {
   
            @Override
            public void onClick(ClickEvent arg0) {
               QuestState.getInstance().getState().removeItem(item);
               RefreshQuestEvent qe = new RefreshQuestEvent();
               EventBus.get().fireEvent(qe);
            }
         });
         links.add(deleteLabel);
      }

      Anchor editLabel=new Anchor("A");
      if (readOnly){
         editLabel.setText("Details");
      } else {
          editLabel.setText("Bewerk");
      }
      editLabel.setStyleName("detailslink");
      editLabel.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent arg0) {
            DialogSelector.showRelevantDialog(item.getTypeName(), item,readOnly,false);
         }
      });

      links.add(editLabel);
      
      result.add(links);

      return result;
   }

   private void refresh() {
      itemGrid.clear();
      QuestDTO quest = QuestState.getInstance().getState();
      int numberDisplayed = 0;
      int totalNumber = 0;
      if (quest != null && quest.getItems() != null) {
         for (QuestItemDTO questItemDTO : quest.getItems()) {
            if (QuestState.getInstance().isTypeVisible(questItemDTO.getTypeName())) {
               itemGrid.add(createItemWidget(questItemDTO));
               numberDisplayed++;
            }
         }
         totalNumber = quest.getItems().size();
      }
      refreshSummaryPanel(numberDisplayed, totalNumber);
   }

   private void refreshSummaryPanel(int numberDisplayed, int totalNumber) {
      summaryPanel.clear();
      Label summary = new Label(numberDisplayed + " van totaal " + totalNumber);
      summaryPanel.add(summary);
   }

   @Override
   public void onUpdateQuestItem(UpdateQuestItemEvent p) {
      refresh();
   }

   @Override
   public void onCreateQuestItem(CreateQuestItemEvent p) {
      refresh();
   }

   @Override
   public void onItemFilter(ItemFilterEvent p) {
      refresh();
   }

   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      refresh();
   }
   
   @Override
   public void resize(int x, int y) {
      scrollPanel.setHeight(y-20+"px");
//      setWidth(x+"px");
   }

}
