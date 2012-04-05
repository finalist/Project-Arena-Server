package nl.kennisnet.arena.client.widget;

import nl.kennisnet.arena.client.dialog.DialogSelector;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemTypes;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.event.ClickPolygonEvent;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.MoveQuestItemEvent;
import nl.kennisnet.arena.client.util.GeomUtil;

import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Polygon;

public class QuestItemMarker {

	private Polygon polygon;
	private Marker marker;

	public QuestItemMarker(final GoogleMap map, final QuestItemDTO item, final boolean readonly) {
	  marker = Marker.create();
	  marker.setPosition(LatLng.create(item.getPoint().getLatitude(), item.getPoint().getLongitude()));
	  marker.setOptions(createOptions(item));    
	  marker.setMap(map);
      createAndSetRadius(item.getRadius(), GeomUtil.createGWTPoint(item.getPoint()), map, QuestItemTypes.getColor(item
            .getTypeName()));
      marker.setDraggable(!readonly);
      if (!readonly) {
    	  marker.addDragEndListenerOnce(new Marker.DragEndHandler() {
			
			@Override
			public void handle(MouseEvent event) {
	            item.setPoint(new SimplePoint(marker.getPosition()));
	            EventBus.get().fireEvent(new MoveQuestItemEvent());
			}
		});
      }

      marker.addClickListener(new Marker.ClickHandler() {
		
		@Override
		public void handle(MouseEvent event) {
			 DialogSelector.showRelevantDialog(item.getTypeName(), item, readonly,false);
			
		}
	});
    

   }

	private void createAndSetRadius(Double radius, LatLng point, GoogleMap map,
			String color) {
		if (polygon != null) {
			polygon.setMap(null);
		}
		if (radius != null && radius > 0) {
			polygon = Polygon.create();
			polygon.setPath(polygonArrayToMvcArray(GeomUtil.createCircle(point,
					radius)));

			polygon.addClickListener(new Polygon.ClickHandler() {
				@Override
				public void handle(MouseEvent event) {
					ClickPolygonEvent qi = new ClickPolygonEvent();
					qi.setClickPoint(event.getLatLng());
					// TODO
					// qi.setSender(event.getSender());
					EventBus.get().fireEvent(qi);
				}
			});
			polygon.setMap(map);
		}
	}

	public MVCArray<LatLng> polygonArrayToMvcArray(LatLng[] latlngArray) {
		MVCArray<LatLng> mvcArray = MVCArray.create();
		for (LatLng latLng : latlngArray) {
			mvcArray.push(latLng);
		}
		return mvcArray;
	}

	private static MarkerOptions createOptions(QuestItemDTO item) {
		MarkerOptions options = MarkerOptions.create();
		MarkerImage icon = QuestItemTypes.getIcon(item.getTypeName());
		options.setIcon(icon);
		options.setDraggable(true);
		options.setTitle(item.getName());
		return options;

	}

	public Marker getMarker(){
		return marker;
	}
	
	public Polygon getPolygon() {
		return polygon;
	}
}
