package nl.kennisnet.arena.client.elements;


public class QuestionElement extends Element{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum TYPE {
		MULTIPLE_CHOICE, OPEN_QUESTION
	}
	
	private Integer questionType = 0;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private Integer correctOption;
	private String description;

	public QuestionElement() {
		super();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public Integer getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(Integer correctOption) {
		this.correctOption = correctOption;
	}

	public TYPE getQuestionTypeAsEnum() {
		if (questionType == null) {
			return TYPE.MULTIPLE_CHOICE;
		}
		switch (questionType) {
		case 0:
			return TYPE.MULTIPLE_CHOICE;
		case 1:
			return TYPE.OPEN_QUESTION;
		default:
			return TYPE.MULTIPLE_CHOICE;
		}
	}

}
