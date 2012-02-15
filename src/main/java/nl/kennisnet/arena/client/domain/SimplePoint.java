package nl.kennisnet.arena.client.domain;

import java.io.Serializable;

import com.google.gwt.maps.client.geom.LatLng;

public class SimplePoint implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   
   private Double latitude;
   private Double longitude;

   public SimplePoint(){
      super();
   }
   
   
   public SimplePoint(Double lattitude, Double longtitude) {
      super();
      this.latitude = lattitude;
      this.longitude = longtitude;
   }
   
   public SimplePoint(LatLng point) {
      super();
      this.latitude = point.getLatitude();
      this.longitude = point.getLongitude();
   }

   public Double getLatitude() {
      return latitude;
   }

   public void setLatitude(Double latitude) {
      this.latitude = latitude;
   }

   public Double getLongitude() {
      return longitude;
   }

   public void setLongitude(Double longitude) {
      this.longitude = longitude;
   }


   @Override
   public String toString() {
      return "SimplePoint [latitude=" + latitude + ", longitude=" + longitude + "]";
   }


   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
      result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
      return result;
   }


   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      SimplePoint other = (SimplePoint) obj;
      if (latitude == null) {
         if (other.latitude != null)
            return false;
      } else if (!latitude.equals(other.latitude))
         return false;
      if (longitude == null) {
         if (other.longitude != null)
            return false;
      } else if (!longitude.equals(other.longitude))
         return false;
      return true;
   }
   
   


   
}
