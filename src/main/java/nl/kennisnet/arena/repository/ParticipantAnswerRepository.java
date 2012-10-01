package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.ParticipantAnswer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantAnswerRepository extends HibernateRepository<ParticipantAnswer>{

	public ParticipantAnswerRepository() {
		super(ParticipantAnswer.class);
	}

}
