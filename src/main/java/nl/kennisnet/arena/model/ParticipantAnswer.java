package nl.kennisnet.arena.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class ParticipantAnswer implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer answer;
	
	private String textAnswer;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Participation participation;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Question question;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Round round;
	
	public enum Result{
		CORRECT,
		INCORRECT,
		ANSWERED,
		UNANSWERED
	}
	
	private String result = Result.UNANSWERED.name();	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Participation getParticipation() {
		return participation;
	}

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
	
	public Integer getAnswer() {
		return answer;
	}
	
	public void setTextAnswer(String textAnswer) {
		answer = 0;
		this.textAnswer = textAnswer;
	}
	
	public String getTextAnswer() {
		return textAnswer;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setRound(Round round) {
		this.round = round;
	}
	
	public Round getRound() {
		return round;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParticipantAnswer other = (ParticipantAnswer) obj;
		return new EqualsBuilder().
			append(this.answer, other.answer).
			append(this.id, other.id).
			append(this.participation, other.participation).
			append(this.round, other.round).
			append(this.question, other.question).
			append(this.result, other.result).
			append(this.textAnswer, other.textAnswer).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(answer).append(id).append(participation).append(question).append(result).append(round).append(textAnswer).toHashCode();
	}
	
}
