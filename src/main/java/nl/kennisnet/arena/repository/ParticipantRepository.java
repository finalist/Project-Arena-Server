package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Participant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantRepository extends HibernateRepository<Participant>{

	public ParticipantRepository() {
		super(Participant.class);
	}

}
