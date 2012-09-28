package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationRepository extends HibernateRepository<Location>{

	public LocationRepository() {
		super(Location.class);
	}

}
