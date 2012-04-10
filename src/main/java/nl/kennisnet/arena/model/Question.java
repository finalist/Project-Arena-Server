package nl.kennisnet.arena.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Question extends Positionable implements DomainObject {

	public enum TYPE {
		OPEN_QUESTION, MULTIPLE_CHOICE
	}
	
	private String text;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private Integer questionType = 0;
	private Integer correctAnswer;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="participationAnswerPrimaryKey.question",cascade=CascadeType.ALL)
	private List<ParticipantAnswer> participantAnswers;

	public Question() {

	}

	public Question(String text, String answer1, String answer2) {
		super();
		this.text = text;
		this.answer1 = answer1;
		this.answer2 = answer2;
	}

	public Question(String text, String answer1, String answer2,
			String answer3, String answer4, int questionType) {
		super();
		this.text = text;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.questionType = questionType;
	}

	public String getText() {
		return text;
	}

	public String getAnswer1() {
		return answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public void setCorrectAnswer(Integer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Integer getCorrectAnswer() {
		return correctAnswer;
	}
	
	public List<ParticipantAnswer> getParticipantAnswers() {
		return participantAnswers;
	}
	
	public void setParticipantAnswers(List<ParticipantAnswer> participantAnswers) {
		this.participantAnswers = participantAnswers;
	}
	
	public void clearParticipantAnswers() {
		this.participantAnswers.clear();
	}
	
	public int getQuestionType() {
		if(questionType == null){
			return 0;
		}
		return questionType;
	}
	
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	
	public TYPE getQuestionTypeAsEnum() {
		if(questionType == null){
			return TYPE.MULTIPLE_CHOICE;
		}
		switch (questionType) {
			case 0: return TYPE.MULTIPLE_CHOICE;
			case 1: return TYPE.OPEN_QUESTION;
			default: return TYPE.MULTIPLE_CHOICE;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Question) {
			Question q = (Question) obj;
			if (this.getId() != null && q.getId() != null) {
				if (q.getId().equals(this.getId())) {
					return true;
				}
			}
		}
		return super.equals(obj);
	}

}
