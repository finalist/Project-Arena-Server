package nl.kennisnet.arena.repository;

import java.util.List;

import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.ParticipationLog;
import nl.kennisnet.arena.model.Quest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
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
		Criteria criteria = getSession().createCriteria(ParticipationLog.class)
				.createCriteria("participation").createCriteria("quest")
				.add(Restrictions.eq("id", questId));

		List<ParticipationLog> list = criteria.list();

		return list;
	}
}
