package nl.kennisnet.arena.client.domain;

import java.io.Serializable;

public class ActionDTO implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   
   private String teamName;
   private Long timeInMillis;
   private SimplePoint point;
   private String action;
   private String answer;
   private Long questId;
   private Long positionableId;
   
   public ActionDTO(){
      super();
   }
   
   public Long getQuestId() {
      return questId;
   }
   public void setQuestId(Long questId) {
      this.questId = questId;
   }
   public String getTeamName() {
      return teamName;
   }
   public void setTeamName(String teamName) {
      this.teamName = teamName;
   }

   public SimplePoint getPoint() {
      return point;
   }
   public Long getTimeInMillis() {
      return timeInMillis;
   }
   public void setTimeInMillis(Long timeInMillis) {
      this.timeInMillis = timeInMillis;
   }
   public void setPoint(SimplePoint point) {
      this.point = point;
   }
   public String getAction() {
      return action;
   }
   public void setAction(String action) {
      this.action = action;
   }
   public String getAnswer() {
      return answer;
   }
   public void setAnswer(String answer) {
      this.answer = answer;
   }
   
	public void setPositionableId(Long positionableId) {
		this.positionableId = positionableId;
	}

	public Long getPositionableId() {
		return positionableId;
	}

   @Override
   public String toString() {
      return "ActionDTO [action=" + action + ", answer=" + answer + ", point=" + point + ", positionableId=" + positionableId
            + ", questId=" + questId + ", teamName=" + teamName + ", timeInMillis=" + timeInMillis + "]";
   }

	
}
