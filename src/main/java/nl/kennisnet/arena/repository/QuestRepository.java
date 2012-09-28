package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Quest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestRepository extends HibernateRepository<Quest>{

	public QuestRepository() {
		super(Quest.class);
	}
	
}
