package nl.kennisnet.arena.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.Point;

public class QuestItemTypes {

//   private static Map<String, Icon> types = new HashMap<String, Icon>();
   private static List<QuestItemType> types= new ArrayList<QuestItemType>();

   public static String QUEST_TYPE_BORDER = "Omheining";
   public static String QUEST_TYPE_STORY = "Verhaal";
   public static String QUEST_TYPE_QUESTION = "Vraag";
   public static String QUEST_TYPE_PHOTO = "Foto";
   public static String QUEST_TYPE_VIDEO = "Video";
   public static String QUEST_TYPE_OBJECT = "Ding";
   public static String QUEST_TYPE_GOAL = "Stortplaats";
   public static String QUEST_TYPE_3D = "Object3D";
   public static String QUEST_TYPE_COMBI = "Combined";

   static {
      types.add(new QuestItemType(QUEST_TYPE_STORY, "text-red.png", "da0000"));
      types.add(new QuestItemType(QUEST_TYPE_PHOTO, "photo.png", "0337d1"));
      types.add(new QuestItemType(QUEST_TYPE_QUESTION, "question.png", "00a700"));
      types.add(new QuestItemType(QUEST_TYPE_3D, "trash.png", "600060"));
      types.add(new QuestItemType(QUEST_TYPE_COMBI, "trash.png", "978000"));
      types.add(new QuestItemType(QUEST_TYPE_VIDEO, "video.png", "800080"));
      
   }

   private static MarkerImage createIcon(String imageURL) {
	  MarkerImage result = MarkerImage.create(imageURL);
      result.setAnchor(Point.create(16, 35));
      return result;
   }

   public static ArrayList<String> getTypes() {
      ArrayList<String> result= new ArrayList<String>();
      for (QuestItemType type : types) {
         result.add(type.getName());
      }
      return result;
   }

   public static MarkerImage getIcon(String typeName) {
      return getType(typeName).getIcon();
   }
   
   public static String getColor(String typeName){
      return getType(typeName).getHexColor();
   }

   private static QuestItemType getType(String typeName){
      for (QuestItemType type : types) {
         if (type.getName().equals(typeName)){
            return type;
         }
      }
      return null;
   }
   
   private static class QuestItemType {

      private final String name;
      private final MarkerImage icon;
      private final String hexColor;

      public QuestItemType(String name, String iconUrl, String color) {
         this.name = name;
         this.hexColor = color;
         this.icon = createIcon(iconUrl);
      }

      public String getName() {
         return name;
      }

      public MarkerImage getIcon() {
         return icon;
      }

      public String getHexColor() {
         return hexColor;
      }

   }

}