package nl.kennisnet.arena.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private Entry[] logEntries;

   public LogDTO() {
   }

   public LogDTO(List<ActionDTO> actions, Collection<TeamDTO> teams, Collection<QuestItemDTO> items) {
      this(actions, createTeamMap(teams), createItemMap(items));
   }

   public LogDTO(List<ActionDTO> actions, Map<String, TeamDTO> teamMap, Map<Long, QuestItemDTO> itemMap) {
      List<Entry> result = new ArrayList<Entry>();
      for (int i = 0; i < actions.size(); i++) {
         ActionDTO actionDTO = actions.get(i);
         TeamDTO team = teamMap.get(actionDTO.getTeamName());
         if (actionDTO.getPositionableId()!=null){
            QuestItemDTO item = itemMap.get(actionDTO.getPositionableId());
            if (item != null) {
               result.add(new Entry(team, item, item.getTypeName(), actionDTO));
            }
         } else {
            result.add(new Entry(team, null, null, actionDTO));
         }
      }
      logEntries = new Entry[result.size()];
      logEntries = result.toArray(logEntries);
   }

   private static Map<String, TeamDTO> createTeamMap(Collection<TeamDTO> teams) {
      Map<String, TeamDTO> teamMap = new HashMap<String, TeamDTO>();
      for (TeamDTO teamDTO : teams) {
         teamMap.put(teamDTO.getName(), teamDTO);
      }
      return teamMap;
   }

   private static Map<Long, QuestItemDTO> createItemMap(Collection<QuestItemDTO> items) {
      Map<Long, QuestItemDTO> itemMap = new HashMap<Long, QuestItemDTO>();
      for (QuestItemDTO questItemDTO : items) {
         itemMap.put(questItemDTO.getId(), questItemDTO);
      }
      return itemMap;
   }

   public List<TeamDTO> getTeams() {
      Set<TeamDTO> teams = new HashSet<TeamDTO>();
      for (int i = 0; i < logEntries.length; i++) {
         Entry entry = logEntries[i];
         teams.add(entry.getTeamDTO());
      }
      List<TeamDTO> result = new ArrayList<TeamDTO>(teams);
      return result;
   }

   public List<ActionDTO> getTypeActions(String typeName) {
      List<ActionDTO> result = new ArrayList<ActionDTO>();
      for (int i = 0; i < logEntries.length; i++) {
         Entry entry = logEntries[i];
         if (entry.getTypeName()!=null&&entry.getTypeName().equals(typeName)) {
            result.add(entry.getActionDTO());
         }
      }
      return result;
   }

   public List<ActionDTO> getTeamActions(String teamName) {
      List<ActionDTO> result = new ArrayList<ActionDTO>();
      for (int i = 0; i < logEntries.length; i++) {
         Entry entry = logEntries[i];
         if (teamName.equals(entry.getTeamDTO().getName())) {
            result.add(entry.getActionDTO());
         }
      }
      return result;
   }

   public List<QuestItemDTO> getQuestItems() {
      return getQuestItems(null);
   }

   public List<QuestItemDTO> getQuestItems(String questItemType) {
      Set<QuestItemDTO> items = new HashSet<QuestItemDTO>();
      if (logEntries!=null){
         for (int i = 0; i < logEntries.length; i++) {
            Entry entry = logEntries[i];
            if (entry!=null&&(entry.getItemDTO()!=null&&questItemType == null) || (questItemType!=null&&questItemType.equals(entry.getTypeName()))) {
               items.add(entry.getItemDTO());
            }
         }
      }
      List<QuestItemDTO> result = new ArrayList<QuestItemDTO>(items);
      return result;
   }

   public ActionDTO getQuestItem(String teamName, QuestItemDTO item) {
      for (int i = 0; i < logEntries.length; i++) {
         Entry entry = logEntries[i];
         if (entry.getItemDTO().equals(item) && entry.getTeamDTO().getName().equals(teamName)) {
            return entry.getActionDTO();
         }
      }
      return null;
   }

   public static class Entry implements Serializable {

      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      private TeamDTO teamDTO;
      private QuestItemDTO itemDTO;
      private String typeName;
      private ActionDTO actionDTO;

      public Entry() {
         super();
      }

      public Entry(TeamDTO teamDTO, QuestItemDTO itemDTO, String typeName, ActionDTO actionDTO) {
         super();
         this.teamDTO = teamDTO;
         this.itemDTO = itemDTO;
         this.typeName = typeName;
         this.actionDTO = actionDTO;
      }

      public TeamDTO getTeamDTO() {
         return teamDTO;
      }

      public QuestItemDTO getItemDTO() {
         return itemDTO;
      }

      public String getTypeName() {
         return typeName;
      }

      public ActionDTO getActionDTO() {
         return actionDTO;
      }

      public void setTeamDTO(TeamDTO teamDTO) {
         this.teamDTO = teamDTO;
      }

      public void setItemDTO(QuestItemDTO itemDTO) {
         this.itemDTO = itemDTO;
      }

      public void setTypeName(String typeName) {
         this.typeName = typeName;
      }

      public void setActionDTO(ActionDTO actionDTO) {
         this.actionDTO = actionDTO;
      }

      @Override
      public String toString() {
         return "Entry [actionDTO=" + actionDTO + ", itemDTO=" + itemDTO + ", teamDTO=" + teamDTO + ", typeName=" + typeName
               + "]";
      }

   }

   @Override
   public String toString() {
      return "LogDTO [logEntries=" + Arrays.toString(logEntries) + "]";
   }

}
