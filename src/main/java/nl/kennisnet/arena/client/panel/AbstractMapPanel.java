package nl.kennisnet.arena.client.panel;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.SelectQuestItemEvent;
import nl.kennisnet.arena.client.event.ZoomToFitEvent;
import nl.kennisnet.arena.client.util.GeomUtil;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.Polyline;

public abstract class AbstractMapPanel extends SimplePanel implements ZoomToFitEvent.Handler,RefreshQuestEvent.Handler,SelectQuestItemEvent.Handler,ResizablePanel {
   

   private static double START_LATITUDE = 52.234528;
   private static double START_LONGTITUDE = 5;
   private static int START_ZOOM = 8;
   private GoogleMap map;
   private boolean needToZoomForTheFirstTime=false;
   
   protected List<Polygon> polygonObjects = new ArrayList<Polygon>();   
   protected List<Marker> markerObjects = new ArrayList<Marker>();   
   protected List<Polyline> polylineObjects = new ArrayList<Polyline>();   
   
   
   public AbstractMapPanel(){
      MapOptions mapOptions = MapOptions.create();
      mapOptions.setCenter(LatLng.create(START_LATITUDE, START_LONGTITUDE));
	  map = (GoogleMap) GoogleMap.create(this.getElement());

	  mapOptions.setScrollwheel(true);
	  mapOptions.setDisableDoubleClickZoom(false);
	  mapOptions.setOverviewMapControl(true);
	  mapOptions.setDraggable(true);
	  mapOptions.setZoom(START_ZOOM);
	  mapOptions.setMapTypeId(MapTypeId.HYBRID);
	  
      map.setOptions(mapOptions);
      
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
            border = LatLngBounds.create();
            for (LatLng point : GeomUtil.createGWTPolygon(questDTO.getBorder())) {
               border.extend(point);
            }
         } else if (questDTO.getItems() != null) {
            border = LatLngBounds.create();

            for (QuestItemDTO itemDTO : questDTO.getItems()) {
               for (LatLng point : GeomUtil.createBoundingBox(itemDTO.getPoint(), itemDTO.getRadius())){
                  border.extend(point);
               }
            }
         }
      }
      if (border != null) {
         map.setCenter(border.getCenter());
      }
   }

   @Override
   public void onSelectQuestItem(SelectQuestItemEvent p) {
      map.panTo(GeomUtil.createGWTPoint(p.getQuestItem().getPoint()));
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
   
   public void clearMap(){
	   for(Polygon polygon : polygonObjects){
		   polygon.setMap(null);
	   }
	   for(Marker marker: markerObjects){
		   marker.setMap((GoogleMap)null);
	   }
	   for(Polyline polyline : polylineObjects){
		   polyline.setMap(null);
	   }
	   polygonObjects.clear();
	   markerObjects.clear();
	   polylineObjects.clear();
   }
   
   protected abstract int getViewId();
   
   protected abstract void refresh();
   
   protected GoogleMap getMapWidget(){
      return map;
   }
   
   @Override
   public void resize(int x, int y) {
	   this.setWidth(x + "px");
	   this.setHeight(y + "px");
	   map.triggerResize();
   }


}
