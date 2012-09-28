package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Positionable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PositionableRepository extends HibernateRepository<Positionable>{

	public PositionableRepository() {
		super(Positionable.class);
	}

}
