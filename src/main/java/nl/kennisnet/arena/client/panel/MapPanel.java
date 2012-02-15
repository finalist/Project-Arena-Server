package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.dialog.DialogSelector;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.domain.SimplePolygon;
import nl.kennisnet.arena.client.event.ClickPolygonEvent;
import nl.kennisnet.arena.client.event.CreateQuestItemEvent;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.ItemFilterEvent;
import nl.kennisnet.arena.client.event.MoveQuestItemEvent;
import nl.kennisnet.arena.client.event.UpdateQuestItemEvent;
import nl.kennisnet.arena.client.event.WallToggleEvent;
import nl.kennisnet.arena.client.util.GeomUtil;
import nl.kennisnet.arena.client.widget.QuestItemMarker;

import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.maps.client.overlay.Polygon;

public class MapPanel extends AbstractMapPanel implements WallToggleEvent.Handler, ItemFilterEvent.Handler,
      MoveQuestItemEvent.Handler, ClickPolygonEvent.Handler, UpdateQuestItemEvent.Handler {

   private static double BORDER_PADDING = 250;

   public MapPanel() {
      super();
      EventBus.get().addHandler(WallToggleEvent.TYPE, this);
      EventBus.get().addHandler(ItemFilterEvent.TYPE, this);
      EventBus.get().addHandler(MoveQuestItemEvent.TYPE, this);
      EventBus.get().addHandler(ClickPolygonEvent.TYPE, this);
      EventBus.get().addHandler(UpdateQuestItemEvent.TYPE, this);

      getMapWidget().addMapClickHandler(new MapClickHandler() {
         public void onClick(MapClickEvent e) {
            Overlay overlay = e.getOverlay();
            LatLng point = e.getLatLng();
            if (overlay == null) {
               createQuestItem(point);
            } else if (overlay instanceof Polygon) {

            } else if (overlay instanceof Marker) {

            }
         }
      });
   }

   private void createQuestItem(LatLng point) {
      String selectedItemType = QuestState.getInstance().getSelectedQuestType();
      if (selectedItemType != null) {

         QuestItemDTO itemDTO = new QuestItemDTO(selectedItemType + " " + QuestState.getInstance().getNumber(selectedItemType),
               selectedItemType);

         itemDTO.setPoint(new SimplePoint(point));
         itemDTO.setRadius(250.0);

         new QuestItemMarker(getMapWidget(), itemDTO, false);
         QuestState.getInstance().getState().addItem(itemDTO);
         CreateQuestItemEvent eventQI = new CreateQuestItemEvent();

         eventQI.setQuestItem(itemDTO);
         
         DialogSelector.showRelevantDialog(selectedItemType, itemDTO, false,true);

         EventBus.get().fireEvent(eventQI);

         refresh();
      }
   }

   protected void refresh() {
      getMapWidget().clearOverlays();
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (questDTO!=null){
         if (questDTO.getBorder() != null) {
            questDTO.setBorder(createWall(questDTO));
         }
   
         if (questDTO != null && questDTO.getItems() != null) {
            for (QuestItemDTO itemDTO : questDTO.getItems()) {
               if (QuestState.getInstance().isTypeVisible(itemDTO.getTypeName())) {
                  new QuestItemMarker(getMapWidget(), itemDTO, false);
               }
            }
         }
   
         if (questDTO.getBorder() != null) {
            Polygon polygon = new Polygon(GeomUtil.createGWTPolygon(questDTO.getBorder()));
            polygon.addPolygonClickHandler(new PolygonClickHandler() {
               @Override
               public void onClick(PolygonClickEvent event) {
                  ClickPolygonEvent qi = new ClickPolygonEvent();
                  qi.setClickPoint(event.getLatLng());
                  qi.setSender(event.getSender());
                  EventBus.get().fireEvent(qi);
               }
            });
            getMapWidget().addOverlay(polygon);
         }
      }
   }

   @Override
   public void onWallToggle(WallToggleEvent p) {
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (p.getWallUp() != null && p.getWallUp().booleanValue()) {
         if (questDTO != null && questDTO.getItems() != null) {
            questDTO.setBorder(createWall(questDTO));
         }
      } else {
         questDTO.setBorder(null);
      }
      refresh();
   }

   private SimplePolygon createWall(QuestDTO questDTO) {
      LatLngBounds border = LatLngBounds.newInstance();
      for (QuestItemDTO itemDTO : questDTO.getItems()) {
         border.extend(GeomUtil.createGWTPoint(itemDTO.getPoint()));
      }
      return new SimplePolygon(GeomUtil.createCircle(border, BORDER_PADDING));
   }

   @Override
   public void onMoveQuestItem(MoveQuestItemEvent p) {
      refresh();
   }

   @Override
   public void onClickPolygon(ClickPolygonEvent p) {
      createQuestItem(p.getClickPoint());
   }

   @Override
   public void onItemFilter(ItemFilterEvent p) {
      refresh();
   }

   @Override
   public void onUpdateQuestItem(UpdateQuestItemEvent p) {
      refresh();
   }

   @Override
   protected int getViewId() {
      return QuestState.DESIGNER_VIEW;
   }

}
