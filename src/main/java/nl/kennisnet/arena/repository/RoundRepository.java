package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Round;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoundRepository extends HibernateRepository<Round>{

	public RoundRepository() {
		super(Round.class);
	}

}
