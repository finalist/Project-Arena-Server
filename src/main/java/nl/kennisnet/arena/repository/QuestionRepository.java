package nl.kennisnet.arena.repository;

import nl.kennisnet.arena.model.Question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionRepository extends HibernateRepository<Question>{

	public QuestionRepository() {
		super(Question.class);
	}
	
}
