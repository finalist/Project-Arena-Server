package nl.kennisnet.arena.repository;

import java.util.List;

import nl.kennisnet.arena.model.ParticipantAnswer;
import nl.kennisnet.arena.model.Participation;
import nl.kennisnet.arena.model.Question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantAnswerRepository extends HibernateRepository<ParticipantAnswer>{

	public ParticipantAnswerRepository() {
		super(ParticipantAnswer.class);
	}
	
	public void evict(ParticipantAnswer answer) {
		getSession().evict(answer);
	}
	
	public ParticipantAnswer findParticipantAnswer(Participation participation, Question question) {
		ParticipantAnswer example = new ParticipantAnswer();
		example.setQuestion(question);
		example.setParticipation(participation);
		return (ParticipantAnswer)getSession().createQuery("from ParticipantAnswer where question.id=:questionId and participation.id=:participationId")
				.setLong("questionId", question.getId())
				.setLong("participationId", participation.getId())
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ParticipantAnswer> findParticipantAnswers(Question question) {
		ParticipantAnswer example = new ParticipantAnswer();
		example.setQuestion(question);
		return getSession().createQuery("from ParticipantAnswer where question.id=:questionId ")
				.setLong("questionId", question.getId())
				.list();
	}
}
