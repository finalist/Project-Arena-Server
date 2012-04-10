package nl.kennisnet.arena.client.util;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.domain.SimplePolygon;

import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.geometry.Spherical;


public class GeomUtil {

	private static double EARTH_RADIUS = 6368137.0;

	private static LatLng[] createPolygone(final LatLng middle, final double diameterInMeters, final int steps) {
		final List<LatLng> result = new ArrayList<LatLng>();
		final double lat1 = middle.lat() * Math.PI/180;
		final double lng1 = middle.lng() * Math.PI/180;
		double brng;
		for (int degrees = 0; degrees < 360; degrees = degrees + (360/steps)) {
			brng = degrees * Math.PI / 180;
			final double dDivR = diameterInMeters / EARTH_RADIUS;
			final double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dDivR) + Math.cos(lat1) * Math.sin(dDivR) * Math.cos(brng));
			final double lng2 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(dDivR) * Math.cos(lat1), Math.cos(dDivR) - Math.sin(lat1) * Math.sin(lat2));
			result.add(LatLng.create(lat2 / Math.PI * 180, lng2 / Math.PI * 180));
		}
		result.add(result.get(0));
		final LatLng[] arrayResult = new LatLng[result.size()];
		return result.toArray(arrayResult);
	}

   public static LatLng[] createCircle(final LatLng middle, final double diameterInMeters) {
      return createPolygone(middle, diameterInMeters, 90);
   }
   
	public static LatLng[] createCircle(final LatLngBounds boundingBox, final double padding) {
		final double radius = Spherical.computeDistanceBetween(boundingBox.getNorthEast(), boundingBox.getCenter()) + padding;
		return GeomUtil.createCircle(boundingBox.getCenter(), radius);
	}

   public static LatLng[] createGWTPolygon(SimplePolygon polygon) {
      if (polygon.getPoints() != null) {
         LatLng[] result = new LatLng[polygon.getPoints().size()];
         for (int i = 0; i < polygon.getPoints().size(); i++) {
            SimplePoint point = polygon.getPoints().get(i);
            result[i] = createGWTPoint(point);
         }
         return result;
      }
      return null;
   }
   
   public static LatLng createGWTPoint(SimplePoint point) {
      return LatLng.create(point.getLatitude(), point.getLongitude());
   }

   public static LatLng[] createBoundingBox(SimplePoint point,double radius){
	   LatLng latlng = createGWTPoint(point);
      return createPolygone(latlng, radius, 4);
   }
   
}
