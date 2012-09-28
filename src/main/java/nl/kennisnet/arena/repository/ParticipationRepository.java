package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Round;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipationRepository extends HibernateRepository<Participation>{

	public ParticipationRepository() {
		super(Participation.class);
	}

	public Participation findParticipation(Participant participant,Quest quest,Round round){
		Participation example = new Participation();
		example.setParticipant(participant);
		example.setQuest(quest);
		example.setRound(round);
		Criteria criteria = getSession().createCriteria(Participation.class).add(Example.create(example));
		criteria.createCriteria("participant").add(Example.create(example.getParticipant()));
		criteria.createCriteria("quest").add(Example.create(example.getQuest()));
		criteria.createCriteria("round").add(Example.create(example.getRound()));
		/*criteria.add(Restrictions.eq("participant", participant));
		criteria.add(Restrictions.eq("quest", quest));
		criteria.add(Restrictions.eq("round", round));*/
		Participation p = (Participation)criteria.uniqueResult();
		return p;
		//return (Participation)getSession().createCriteria(Participation.class).add(Example.create(example)).uniqueResult();
	}
}
