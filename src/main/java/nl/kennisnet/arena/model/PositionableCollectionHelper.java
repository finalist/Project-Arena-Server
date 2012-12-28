package nl.kennisnet.arena.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Point;

/**
 * Filter positionables
 * 
 * @author pvink
 * @author pmaas
 */
public class PositionableCollectionHelper<D extends Positionable> {

	private List<D> positionables;

	public PositionableCollectionHelper(List<D> positionables) {
		this.positionables = positionables;
	}

	public List<D> all() {
		return positionables;
	}

	public <V extends Positionable> PositionableCollectionHelper<V> filter(Class<V> clazz) {
		return PositionableCollectionHelper.filter(positionables, clazz);
	}

	public PositionableCollectionHelper<D> nearBy(Point p) {
		return PositionableCollectionHelper.nearBy(positionables, p);
	}	


	public D first(){
		return positionables.get(0);
	}

	public static <T extends Positionable> PositionableCollectionHelper<T> nearBy(final List<T> positionables, final Point curLocation) {

	   List<SpacedLocation> spaced = new ArrayList<SpacedLocation>();
		for (Positionable p : positionables) {
		   spaced.add(new SpacedLocation(p, p.getLocation().getPoint().distance(curLocation)));
         
      }

		Collections.sort(spaced);

		return new PositionableCollectionHelper<T>(Lists.transform(spaced, new Function<SpacedLocation, T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T apply(SpacedLocation sl) {
				return (T)sl.getP();
			}

		}));		
	}

	public static <T extends Positionable> PositionableCollectionHelper<T> filter(List<? extends Positionable> positionables, Class<T> clazz) {
		return new PositionableCollectionHelper<T>( Lists.newArrayList( Iterables.filter(positionables, clazz) ) );
	}	

	private static class SpacedLocation implements Comparable<SpacedLocation>{
		private final Positionable p;
		private final double distance;
		public SpacedLocation(Positionable p, double distance){
			this.p = p;
			this.distance = distance;
		}
		public Positionable getP() {
			return p;
		}
		public double getDistance() {
			return distance;
		}
		@Override
		public int compareTo(SpacedLocation o) {
			return Double.compare(distance, o.getDistance());
		}
	}
}