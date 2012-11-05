package nl.kennisnet.arena.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class ParticipantAnswer implements DomainObject {

	@Id
	private ParticipationAnswerPrimaryKey participationAnswerPrimaryKey = new ParticipationAnswerPrimaryKey();
	
	private Integer answer;
	
	private String textAnswer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Round round;
	
	public enum Result{
		CORRECT,
		INCORRECT,
		ANSWERED,
		UNANSWERED
	}
	
	private String result = Result.UNANSWERED.name();
	
	public ParticipationAnswerPrimaryKey getParticipationAnswerPrimaryKey() {
		return participationAnswerPrimaryKey;
	}
	
	public void setParticipationAnswerPrimaryKey(
			ParticipationAnswerPrimaryKey participationAnswerPrimaryKey) {
		this.participationAnswerPrimaryKey = participationAnswerPrimaryKey;
	}
	
	public long getParticipationId() {
		return participationAnswerPrimaryKey.getParticipationId();
	}
	
	public Participation getParticipation() {
		return participationAnswerPrimaryKey.participation;
	}
	
	public void setParticipation(Participation participation) {
		participationAnswerPrimaryKey.participation = participation;
	}
	
	
	public Question getQuestion() {
		return participationAnswerPrimaryKey.question;
	}

	public void setQuestion(Question question) {
		participationAnswerPrimaryKey.question = question;
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
		if(obj instanceof ParticipantAnswer){
			if( ((ParticipantAnswer)obj).getParticipationId() == (this.getParticipationId())
					&& ((ParticipantAnswer)obj).getQuestion().equals(this.getQuestion()) ){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getParticipationId()).append(this.getQuestion()).toHashCode();
	}
	
	@Embeddable
	public static class ParticipationAnswerPrimaryKey implements Serializable{

		private static final long serialVersionUID = 1L;

		@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
		private Participation participation;
		
		@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
		private Question question;
		
		
		public Participation getParticipation() {
			return participation;
		}
		
		public void setParticipation(Participation participation) {
			this.participation = participation;
		}
		
		public long getParticipationId() {
			return participation.getId();
		}

		public Question getQuestion() {
			return question;
		}

		public void setQuestion(Question question) {
			this.question = question;
		}

	}
	
}
