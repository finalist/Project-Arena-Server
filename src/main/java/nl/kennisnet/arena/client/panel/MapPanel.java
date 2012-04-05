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

import com.google.maps.gwt.client.GoogleMap.ClickHandler;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Polygon;

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


      getMapWidget().addClickListener(new ClickHandler() {
		
    	@Override
		public void handle(MouseEvent event) {
            LatLng point = event.getLatLng();
            createQuestItem(point);     
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

         QuestItemMarker questItemMarker = new QuestItemMarker(getMapWidget(), itemDTO, false);
         markerObjects.add(questItemMarker.getMarker());
         polygonObjects.add(questItemMarker.getPolygon());
         
         QuestState.getInstance().getState().addItem(itemDTO);
         CreateQuestItemEvent eventQI = new CreateQuestItemEvent();

         eventQI.setQuestItem(itemDTO);
         
         DialogSelector.showRelevantDialog(selectedItemType, itemDTO, false,true);

         EventBus.get().fireEvent(eventQI);

         refresh();
      }
   }

   protected void refresh() {
	  clearMap();
      QuestDTO questDTO = QuestState.getInstance().getState();
      if (questDTO!=null){
         if (questDTO.getBorder() != null) {
            questDTO.setBorder(createWall(questDTO));
         }
   
         if (questDTO != null && questDTO.getItems() != null) {
            for (QuestItemDTO itemDTO : questDTO.getItems()) {
               if (QuestState.getInstance().isTypeVisible(itemDTO.getTypeName())) {
                  QuestItemMarker questItemMarker = new QuestItemMarker(getMapWidget(), itemDTO, false);
                  markerObjects.add(questItemMarker.getMarker());
                  polygonObjects.add(questItemMarker.getPolygon());
               }
            }
         }
   
         if (questDTO.getBorder() != null) {
            final Polygon polygon = Polygon.create();
            polygon.setPath(polygonArrayToMvcArray(GeomUtil.createGWTPolygon(questDTO.getBorder())));
            polygon.addClickListener(new Polygon.ClickHandler() {
				            	
				@Override
				public void handle(MouseEvent event) {
					ClickPolygonEvent qi = new ClickPolygonEvent();
					qi.setClickPoint(event.getLatLng());
	                //TODO
					//qi.setSender(event.getSender());
	                EventBus.get().fireEvent(qi);
				}
			});
            polygon.setMap(getMapWidget());
            polygonObjects.add(polygon);
         }
      }
   }
   
   public MVCArray<LatLng> polygonArrayToMvcArray(LatLng[] latlngArray){
	   MVCArray<LatLng> mvcArray = MVCArray.create();
       for(LatLng latLng: latlngArray){
    	   mvcArray.push(latLng);	   
       }
       return mvcArray;
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
      LatLngBounds border = LatLngBounds.create();
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
