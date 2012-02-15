package nl.kennisnet.arena.client.domain;

import java.io.Serializable;

public class TeamDTO implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   
   private String name;
   private String color;
   private Integer progress;
   private Integer score;
   private Long id;
   
   public TeamDTO(){
      super();
   }
   
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getColor() {
      return color;
   }
   public void setColor(String color) {
      this.color = color;
   }
   public Integer getProgress() {
      return progress;
   }
   public void setProgress(Integer progress) {
      this.progress = progress;
   }
   public Integer getScore() {
      return score;
   }
   public void setScore(Integer score) {
      this.score = score;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String toString() {
      return "TeamDTO [color=" + color + ", id=" + id + ", name=" + name + ", progress=" + progress + ", score=" + score + "]";
   }


}
