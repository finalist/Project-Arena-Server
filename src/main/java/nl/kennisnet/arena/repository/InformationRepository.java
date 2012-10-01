package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Information;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InformationRepository extends HibernateRepository<Information>{

	public InformationRepository() {
		super(Information.class);
	}

}
