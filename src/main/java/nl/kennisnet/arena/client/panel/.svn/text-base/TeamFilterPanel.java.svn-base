package nl.kennisnet.arena.client.panel;

import java.util.List;

import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.LogEvent;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.event.TeamFilterEvent;
import nl.kennisnet.arena.client.widget.MultiSelectList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class TeamFilterPanel extends SidePanel implements RefreshQuestEvent.Handler, RefreshQuestLogEvent.Handler{

   private final MultiSelectList filter;
   private final Label message;

   
   public TeamFilterPanel() {
      super("Toon","Toon de paden van de teams",true);
      filter = new MultiSelectList();
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
      EventBus.get().addHandler(RefreshQuestLogEvent.TYPE, this);
      add(createTeamPanel());
      message=new Label();
      refresh();
   }


   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      refresh();
   }

   private void refresh() {
      filter.clear();
      message.setText("");
      if (QuestState.getInstance().getLog() != null) {
         List<TeamDTO> teams = QuestState.getInstance().getLog().getTeams();
         if (teams != null && teams.size() > 0) {
            for (TeamDTO teamDTO : teams) {
               filter.addOption(teamDTO.getName(), teamDTO.getName());
            }
            synchPanelFromFilter();
         } else {
            message.setText("Er zijn nog geen teams die deze quest gestart zijn");
         }
      }
   }

   @Override
   public void onRefreshQuestLog(RefreshQuestLogEvent p) {
      EventBus.get().fireEvent(new LogEvent("refreshing Team filter !"));
      refresh();
   }
   
   private Panel createTeamPanel(){
      SimplePanel filterPanel=new SimplePanel();
      
      filterPanel.getElement().setId("teamfilter");


      filter.addValueChangeHandler(new ValueChangeHandler<Object>() {
         
         @Override
         public void onValueChange(ValueChangeEvent<Object> event) {
            if (filter.getValue()!=null){
               synchFilterFromPanel();
               EventBus.get().fireEvent(new TeamFilterEvent());
            }
         }
      });
      filterPanel.add(filter);
      return filterPanel;
   }

   private void synchFilterFromPanel(){
      List<String> filterValues=(List<String>)filter.getValue();
      for (TeamDTO teamDTO : QuestState.getInstance().getLog().getTeams()) {
         String teamName=teamDTO.getName();
         if (QuestState.getInstance().isTeamVisible(teamName)!=filterValues.contains(teamName)){
            QuestState.getInstance().toggleTeamVisible(teamName);
         }
      }
   }

   private void synchPanelFromFilter(){
      List<String> filterValues=(List<String>)filter.getValue();
      if (QuestState.getInstance().getLog()!=null){
         for (TeamDTO teamDTO : QuestState.getInstance().getLog().getTeams()) {
            String teamName=teamDTO.getName();
            if (!QuestState.getInstance().isTeamVisible(teamName)&&filterValues.contains(teamName)){
               filterValues.remove(teamName);
            } else if (QuestState.getInstance().isTeamVisible(teamName)&&!filterValues.contains(teamName)){
               filterValues.add(teamName);
            }
         }
      }
      filter.setValue(filterValues, false);
   }

}
