package nl.kennisnet.arena.formats.convert;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.PositionableCollectionHelper;
import nl.kennisnet.arena.services.factories.GeomUtil;

import org.junit.Test;

import com.vividsolutions.jts.geom.Point;

public class DistanceSortTest {

//   @Test
//   public void testNearBy(){
//      List<Information> informations=new ArrayList<Information>();
//      informations.add(createInformation(3));
//      informations.add(createInformation(2));
//      informations.add(createInformation(1));
//      PositionableCollectionHelper<Information> helper=new PositionableCollectionHelper<Information>(informations);
//      PositionableCollectionHelper<Information> test=helper.nearBy(GeomUtil.createJTSPoint(5, 50));
//      assertNotNull(test);
//   }
//
//   
//   private Information createInformation(int number){
//      Information information=new Information("testName"+number,"testText"+number);
//      double distance=number;
//      distance=distance/1000;
//      Point point=GeomUtil.createJTSPoint(5, 50+distance);
//      information.getPoi().setLocation(new Location(point, 0.0, 100000.0, 100000.0));
//      return information;
//      
//   }
   

}
