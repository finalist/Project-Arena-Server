package nl.kennisnet.arena.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.geom.LatLng;

public class SimplePolygon implements Serializable{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   
   private List<SimplePoint> points;
   
   public SimplePolygon(){
      super();
   }
   
   public SimplePolygon(LatLng[] points){
      super();
      if (points!=null&&points.length>0){
         this.points=new ArrayList<SimplePoint>(points.length);
         for (int i = 0; i < points.length; i++) {
            this.points.add(new SimplePoint(points[i]));
         }
      }
   }

//   public LatLng[] getGWTPolygon(){
//      if (points!=null){
//         LatLng[] result=new LatLng[points.size()];
//         for (int i = 0; i < points.size(); i++) {
//            SimplePoint point = points.get(i);
//            result[i]=point.getGWTPoint();
//         }
//         return result;
//      }
//      return null;
//   }

   public List<SimplePoint> getPoints() {
      return points;
   }

   public void setPoints(List<SimplePoint> points) {
      this.points = points;
   }

   
   
   
}
