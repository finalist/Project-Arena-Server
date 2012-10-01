package nl.kennisnet.arena.repository;

import java.util.List;

import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Quest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipationLogRepository extends
		HibernateRepository<ParticipationLog> {

	public ParticipationLogRepository() {
		super(ParticipationLog.class);
	}

	public void delete(ParticipationLog log) {
		getSession().delete(log);
	}

	public List<ParticipationLog> getParticipationLogsByQuest(long questId) {
		ParticipationLog example = new ParticipationLog();
		Participation exampleParticipation = new Participation();
		example.setParticipation(exampleParticipation);
		Quest exampleQuest = new Quest();
		exampleQuest.setId(questId);
		exampleParticipation.setQuest(exampleQuest);
		Criteria criteria = getSession().createCriteria(ParticipationLog.class)
				.add(Example.create(example)).createCriteria("participation")
				.createCriteria("quest").add(Example.create(exampleQuest.getId()));

		List<ParticipationLog> list = criteria.list();

		return list;
	}
}
