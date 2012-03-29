package nl.kennisnet.arena.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParticipantAnswer implements DomainObject {

	@Id
	private ParticipationAnswerPrimaryKey participationAnswerPrimaryKey = new ParticipationAnswerPrimaryKey();
	
	private Integer answer;
	
	private String textAnswer;
	
	public ParticipationAnswerPrimaryKey getParticipationAnswerPrimaryKey() {
		return participationAnswerPrimaryKey;
	}
	
	public void setParticipationAnswerPrimaryKey(
			ParticipationAnswerPrimaryKey participationAnswerPrimaryKey) {
		this.participationAnswerPrimaryKey = participationAnswerPrimaryKey;
	}
	
	public long getParticipationtId() {
		return participationAnswerPrimaryKey.participationtId;
	}
	
	public void setParticipationtId(long participationtId) {
		participationAnswerPrimaryKey.participationtId = participationtId;
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

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ParticipantAnswer){
			if( ((ParticipantAnswer)obj).getParticipationtId() == (this.getParticipationtId())
					&& ((ParticipantAnswer)obj).getQuestion().equals(this.getQuestion()) ){
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Embeddable
	public static class ParticipationAnswerPrimaryKey implements Serializable{

		private static final long serialVersionUID = 1L;

		private long participationtId;
		
		@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
		private Question question;
		
		public long getParticipationtId() {
			return participationtId;
		}

		public void setParticipationtId(long participationtId) {
			this.participationtId = participationtId;
		}

		public Question getQuestion() {
			return question;
		}

		public void setQuestion(Question question) {
			this.question = question;
		}

	}
	
}
