package nl.kennisnet.arena.services.factories;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.domain.SimplePolygon;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.operation.distance.DistanceOp;

public class GeomUtil {
   
   private static double EARTH_RADIUS = 6368137.0;
   
   private static GeometryFactory getGeometryFactory(){
      return new GeometryFactory(new PrecisionModel(),4326);
      
   }
   
   public static Polygon createJTSPolygon(SimplePolygon polygon){
      Polygon result=null;
      if (polygon!=null){
         GeometryFactory gf=getGeometryFactory();
         Coordinate[] coordinates=new Coordinate[polygon.getPoints().size()];
         for (int i = 0; i < polygon.getPoints().size(); i++) {
            coordinates[i]=createJTSCoordinate(polygon.getPoints().get(i));
            
         }
         LinearRing linearRing=gf.createLinearRing(coordinates);
         result=gf.createPolygon(linearRing, null);
      }
      return result;
      
   }
   
   public static Coordinate createJTSCoordinate(SimplePoint point){
      return new Coordinate(point.getLongitude(),point.getLatitude());
   }

   public static Point createJTSPoint(SimplePoint point){
      GeometryFactory gf=getGeometryFactory();
      return gf.createPoint(createJTSCoordinate(point));
   }

   public static Point createJTSPoint(double lat,double lon){
      GeometryFactory gf=getGeometryFactory();
      return gf.createPoint(new Coordinate(lon,lat));
   }

   
   
   public static SimplePoint createSimplePoint(Point point) {
      return new SimplePoint( point.getY(),point.getX());
   }

   public static SimplePoint createSimplePoint(Coordinate coordinate) {
      return new SimplePoint(coordinate.y,coordinate.x);
   }

   public static SimplePolygon createSimplePolygon(Polygon polygon) {
      SimplePolygon result = null;
      if (polygon != null) {
         Coordinate[] coordinates = polygon.getBoundary().getCoordinates();
         if (coordinates != null) {
            List<SimplePoint> points=new ArrayList<SimplePoint>();
            for (int i = 0; i < coordinates.length; i++) {
               points.add(createSimplePoint(coordinates[i]));
            }
            result=new SimplePolygon();
            result.setPoints(points);
         }
      }
      return result;

   }
   
   public static double calculateDistanceInMeters(Point x,Point y){
      double distanceInRad=DistanceOp.distance(x,y);
      double result=distanceInRad*(Math.PI/180)*EARTH_RADIUS;
      return result;
   }

}
