package nl.kennisnet.arena.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ParticipantAnswer implements DomainObject {

	@Id
	private ParticipationAnswerPrimaryKey participationAnswerPrimaryKey = new ParticipationAnswerPrimaryKey();
	
	private Integer answer;
	
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

		private long participationtId;
		
		@ManyToOne(cascade = CascadeType.ALL)
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
