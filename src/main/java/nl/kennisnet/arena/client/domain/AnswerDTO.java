package nl.kennisnet.arena.client.domain;

import java.io.Serializable;

public class AnswerDTO implements Serializable {

	public enum AnswerType {
		TEXT_ANSWER,
		MULTIPLE_CHOICE
	}
	
	public enum Result{
		CORRECT,
		INCORRECT,
		ANSWERED,
		UNANSWERED
	}
	
	private static final long serialVersionUID = 1L;

	private String answerType;
	
	private Integer answer = 0;

	private String textAnswer;

	private String playerColor;
	
	private String playerName;

	private String questionName;

	private String questionDescription;

	private String result;
	
	private long questId;
	
	private long questionId;
	
	private long participationId;
	
	private RoundDTO round; 
	
	public AnswerDTO() {

	}
	
	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType.toString();
	}
	
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	
	public String getAnswerType() {
		return answerType;
	}

	public String getAnswer() {
		if (answer != 0) {
			return answerIntToLetter(answer + "");
		} else {
			return textAnswer;
		}
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(String playerColor) {
		this.playerColor = playerColor;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public long getQuestionId() {
		return questionId;
	}
	
	public long getQuestId() {
		return questId;
	}
	
	public void setQuestId(long questId) {
		this.questId = questId;
	}
	
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	
	public long getParticipationId() {
		return participationId;
	}
	
	public void setParticipationId(long participationId) {
		this.participationId = participationId;
	}
	
	public String getResult() {
		return result;
	}

	public RoundDTO getRound() {
		return round;
	}
	
	public void setRound(RoundDTO round) {
		this.round = round;
	}
	
	private String answerIntToLetter(String answer) {
		try {
			int q = Integer.parseInt(answer.trim());
			switch (q) {
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
			default:
				return "";
			}
		} catch (NumberFormatException ne) {
			return answer;
		} catch (IndexOutOfBoundsException ie) {
			return answer;
		}
	}
}
