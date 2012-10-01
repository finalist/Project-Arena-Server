package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantRepository extends HibernateRepository<Participant>{

	public ParticipantRepository() {
		super(Participant.class);
	}

	public Participant findParticipant(String name){
		Participant example = new Participant();
		example.setName(name);
		Criteria criteria = getSession().createCriteria(Participation.class).add(Example.create(example));
		Participant p = (Participant)criteria.uniqueResult();
		return p;
	}
	
}
