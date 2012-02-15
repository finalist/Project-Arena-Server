package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.SelectQuestItemEvent;
import nl.kennisnet.arena.client.event.ZoomToFitEvent;
import nl.kennisnet.arena.client.util.GeomUtil;

import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class AbstractMapPanel extends SimplePanel implements ZoomToFitEvent.Handler,RefreshQuestEvent.Handler,SelectQuestItemEvent.Handler,ResizablePanel {
   

   private static double START_LATITUDE = 53.234528;
   private static double START_LONGTITUDE = 3.0;
   private static int START_ZOOM = 8;
   private MapWidget mapWidget;
   private boolean needToZoomForTheFirstTime=false;
   
   
   public AbstractMapPanel(){
      mapWidget = new MapWidget(LatLng.newInstance(START_LATITUDE, START_LONGTITUDE), 13);
      MapUIOptions options = mapWidget.getDefaultUI();
      options.setScrollwheel(true);
      options.setDoubleClick(false);
      options.setLargeMapControl3d(true);
      mapWidget.setUI(options);
      mapWidget.setDoubleClickZoom(false);
      mapWidget.setDraggable(true);
      mapWidget.setZoomLevel(START_ZOOM);
      mapWidget.setCurrentMapType(MapType.getHybridMap());
      mapWidget.setGoogleBarEnabled(true);

      add(mapWidget);

      EventBus.get().addHandler(ZoomToFitEvent.TYPE, this);
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
      EventBus.get().addHandler(SelectQuestItemEvent.TYPE, this);
      refresh();
   }

   public void zoomToFit() {
      LatLngBounds border = null;
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (questDTO != null&&questDTO.getItems()!=null&&questDTO.getItems().size()>0) {
         if (questDTO.getBorder() != null) {
            border = LatLngBounds.newInstance();
            for (LatLng point : GeomUtil.createGWTPolygon(questDTO.getBorder())) {
               border.extend(point);
            }
         } else if (questDTO.getItems() != null) {
            border = LatLngBounds.newInstance();

            for (QuestItemDTO itemDTO : questDTO.getItems()) {
               for (LatLng point : GeomUtil.createBoundingBox(itemDTO.getPoint(), itemDTO.getRadius())){
                  border.extend(point);
               }
            }
         }
      }
      if (border != null) {
         mapWidget.setCenter(border.getCenter(), mapWidget.getBoundsZoomLevel(border));
      }
   }

   @Override
   public void onSelectQuestItem(SelectQuestItemEvent p) {
      mapWidget.panTo(GeomUtil.createGWTPoint(p.getQuestItem().getPoint()));
   }

   
   @Override
   public void onZoomToFit(ZoomToFitEvent p) {
      if (QuestState.getInstance().getCurrentView() == getViewId()) {
         zoomToFit();
      }
   }
   
   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      refresh();
      zoomIfNeeded();
   }

   protected void triggerZoomForTheFirstTime(){
      needToZoomForTheFirstTime=true;
   }
   
   protected void zoomIfNeeded(){
      if (needToZoomForTheFirstTime){
         zoomToFit();
         needToZoomForTheFirstTime=false;
      }
      
   }
   
   protected abstract int getViewId();
   
   protected abstract void refresh();
   
   protected MapWidget getMapWidget(){
      return mapWidget;
   }
   
   @Override
   public void resize(int x, int y) {
      mapWidget.setHeight(y + "px");
      mapWidget.setWidth(x + "px");
   }


}
