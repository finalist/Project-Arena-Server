package nl.kennisnet.arena.client.widget;

import nl.kennisnet.arena.client.dialog.DialogSelector;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemTypes;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.event.ClickPolygonEvent;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.MoveQuestItemEvent;
import nl.kennisnet.arena.client.util.GeomUtil;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polygon;

public class QuestItemMarker extends Marker {

   private Polygon polygon;
   private MapWidget mapWidget;

   public QuestItemMarker(final MapWidget map, final QuestItemDTO item, final boolean readonly) {
      super(GeomUtil.createGWTPoint(item.getPoint()), createOptions(item));
      mapWidget = map;
      map.addOverlay(this);
      createAndSetRadius(item.getRadius(), GeomUtil.createGWTPoint(item.getPoint()), map, QuestItemTypes.getColor(item
            .getTypeName()));
      setDraggingEnabled(!readonly);
      if (!readonly) {
         this.addMarkerDragEndHandler(new MarkerDragEndHandler() {
            @Override
            public void onDragEnd(MarkerDragEndEvent event) {
               createAndSetRadius(item.getRadius(), event.getSender().getLatLng(), map, QuestItemTypes.getColor(item
                     .getTypeName()));
               item.setPoint(new SimplePoint(event.getSender().getLatLng()));
               EventBus.get().fireEvent(new MoveQuestItemEvent());
            }

         });
      }

      this.addMarkerClickHandler(new MarkerClickHandler() {
         @Override
         public void onClick(MarkerClickEvent event) {
            DialogSelector.showRelevantDialog(item.getTypeName(), item, readonly,false);
         }
      });

   }

   private void createAndSetRadius(Double radius, LatLng point, MapWidget map, String color) {
      if (polygon != null) {
         mapWidget.removeOverlay(polygon);
      }
      if (radius != null && radius > 0) {
         polygon = new Polygon(GeomUtil.createCircle(point, radius));
         polygon.addPolygonClickHandler(new PolygonClickHandler() {
            @Override
            public void onClick(PolygonClickEvent event) {
               ClickPolygonEvent qi = new ClickPolygonEvent();
               qi.setClickPoint(event.getLatLng());
               qi.setSender(event.getSender());
               EventBus.get().fireEvent(qi);
            }
         });
         map.addOverlay(polygon);
      }
   }

   private static MarkerOptions createOptions(QuestItemDTO item) {
      MarkerOptions options = MarkerOptions.newInstance();
      Icon icon = QuestItemTypes.getIcon(item.getTypeName());
      options.setIcon(icon);
      options.setDraggable(true);
      options.setTitle(item.getName());
      return options;

   }

}
