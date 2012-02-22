package nl.kennisnet.arena.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Question extends Positionable implements DomainObject {

	@Lob
	private String text;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private Integer correctAnswer;
	
	public Question() {

	}

	public Question(String text, String answer1, String answer2) {
		super();
		this.text = text;
		this.answer1 = answer1;
		this.answer2 = answer2;
	}

	public Question(String text, String answer1, String answer2, String answer3, String answer4) {
		super();
		this.text = text;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
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
   
   @Override
   public boolean equals(Object obj) {
	   if(obj instanceof Question){
		   Question q = (Question)obj;
		   if(q.getId().equals(this.getId())){
			   return true;
		   }
	   }
	   return super.equals(obj);
   }
   

}
