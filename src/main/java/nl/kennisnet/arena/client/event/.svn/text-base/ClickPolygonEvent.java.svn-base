package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Overlay;

public class ClickPolygonEvent extends GwtEvent<ClickPolygonEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onClickPolygon(ClickPolygonEvent p);
   }

   @Override
   protected void dispatch(ClickPolygonEvent.Handler handler) {
      handler.onClickPolygon(this);
   }

   @Override
   public GwtEvent.Type<ClickPolygonEvent.Handler> getAssociatedType() {
      return TYPE;
   }

   public static final GwtEvent.Type<ClickPolygonEvent.Handler> TYPE = new GwtEvent.Type<ClickPolygonEvent.Handler>();

   private LatLng clickPoint;
   private Overlay sender;
   public LatLng getClickPoint() {
      return clickPoint;
   }

   public void setClickPoint(LatLng clickPoint) {
      this.clickPoint = clickPoint;
   }

   public Overlay getSender() {
      return sender;
   }

   public void setSender(Overlay sender) {
      this.sender = sender;
   }
   
   
   
   
   
}
