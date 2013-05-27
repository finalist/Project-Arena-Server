package nl.kennisnet.arena.client;

import java.util.List;

import nl.kennisnet.arena.client.domain.AnswerDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.event.ScreenSwitchEvent;
import nl.kennisnet.arena.client.panel.AnswerCheckPanel;
import nl.kennisnet.arena.client.panel.HeaderPanel;
import nl.kennisnet.arena.client.panel.ResizablePanel;
import nl.kennisnet.arena.client.panel.ScoreGridPanel;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ArenaWA implements EntryPoint, ScreenSwitchEvent.Handler, RefreshQuestEvent.Handler {

   private static int LOG_REFRESH_RATE = 1000 * 60;

   private SimplePanel contentView;
   private ArenaDesigner designerPanel = new ArenaDesigner();
   private ArenaMonitor monitorPanel;
   private Panel answerCheckPanel;
   private Panel scorePanel;
   private HeaderPanel headerPanel;
   private Timer logRefreshTrigger;

   @Override
   public void onModuleLoad() {
      EventBus.get().addHandler(ScreenSwitchEvent.TYPE, this);
      EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
      RootPanel panel = RootPanel.get("content");

      headerPanel = new HeaderPanel();

      panel.add(headerPanel);
      contentView = new SimplePanel();
      panel.add(contentView);
      handleLoad();
      createState();
      refreshContent();
      Window.addResizeHandler(new ResizeHandler() {
         public void onResize(ResizeEvent event) {
            resize();
         }
      });
   }

   private void resize() {
      ((ResizablePanel) contentView.getWidget()).resize(Window.getClientWidth(), Window.getClientHeight() - 84);
   }

   private void refreshContent() {
      contentView.clear();
      switch (QuestState.getInstance().getCurrentView()) {
      case QuestState.DESIGNER_VIEW:
         contentView.add(designerPanel);
         break;
      case QuestState.MONITOR_VIEW:
         contentView.add(getMonitorPanel());
         break;
      case QuestState.ANSWER_VIEW:
          contentView.add(getAnswerCheckPanel());
          break;
      case QuestState.SCORE_VIEW:
         contentView.add(getScorePanel());
         break;
      }

      resize();
   }

   private void createState() {
      if (QuestState.getInstance().getState() == null) {
         QuestDTO quest = new QuestDTO();
         quest.setName("");
         quest.setEmailOwner("-");
         RoundDTO roundDTO = new RoundDTO("Ronde 1");
     	 quest.setActiveRound(roundDTO);
     	 quest.getRounds().add(roundDTO);
    	 QuestState.getInstance().setState(quest);
      }
   }

   private void handleLoad() {
      final String questId = com.google.gwt.user.client.Window.Location.getParameter("arenaId");
      if (questId != null) {
         Window.setStatus("loading: " + questId);
         GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
         try {
            questService.load(Long.valueOf(questId), new AsyncCallback<QuestDTO>() {

               @Override
               public void onSuccess(QuestDTO arg0) {
                  Window.setStatus("Arena loaded succesfully!");
                  QuestState.getInstance().setState(arg0);
                  EventBus.get().fireEvent(new RefreshQuestEvent());
                  refreshActionLog();
                  refreshAnswers();
                  resize();
                  designerPanel.zoomMap();
               }

               @Override
               public void onFailure(Throwable arg0) {
                  com.google.gwt.user.client.Window.alert("Het laden van de Arena is mislukt : " + arg0.getLocalizedMessage());
               }
            });

         } catch (Exception e) {
            com.google.gwt.user.client.Window.alert("error: " + e.getMessage());
         }
      }
   }
   
   private void refreshAnswers(){
	   final QuestDTO quest = QuestState.getInstance().getState();
	   if (quest != null && quest.getId() != null) {
		   GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
		   try {
	            questService.getAnswer(quest.getId(), new AsyncCallback<List<AnswerDTO>>() {
	            	@Override
	                public void onFailure(Throwable arg0) {
	                   Window.alert("Het laden van de antwoorden is mislukt: " + arg0.getLocalizedMessage());
	                }

					@Override
					public void onSuccess(List<AnswerDTO> arg0) {
						QuestState.getInstance().setAnswers(arg0);
					}
	            });	      
	       } catch (Exception e) {
	       }	
	   }
   }

   private void refreshActionLog() {
      final QuestDTO quest = QuestState.getInstance().getState();
      if (quest != null && quest.getId() != null) {
         GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
         try {
            questService.getLog(quest.getId(), new AsyncCallback<LogDTO>() {

               @Override
               public void onFailure(Throwable arg0) {
                  Window.alert("Het laden van de spelgeschiedenis is mislukt: " + arg0.getLocalizedMessage());

               }

               @Override
               public void onSuccess(LogDTO log) {
                  QuestState.getInstance().setLog(log);
                  EventBus.get().fireEvent(new RefreshQuestLogEvent());
               }
            });
         } catch (Exception e) {
            Window.alert("This went wrong " + e.getMessage());
         }
      }
   }

   @Override
   public void onRefreshQuest(RefreshQuestEvent p) {
      if (logRefreshTrigger == null) {
         Timer refreshTrigger = new Timer() {

            @Override
            public void run() {
               refreshActionLog();
               refreshAnswers();
            }

         };
         refreshTrigger.scheduleRepeating(LOG_REFRESH_RATE);
      }
   }

   public ArenaMonitor getMonitorPanel() {
      if (monitorPanel == null) {
         monitorPanel = new ArenaMonitor();
      }
      return monitorPanel;
   }

   public Panel getAnswerCheckPanel() {
      if (answerCheckPanel == null) {
    	  answerCheckPanel = new AnswerCheckPanel();
      }
      return answerCheckPanel;
   }

   public Panel getScorePanel() {
      if (scorePanel == null) {
         scorePanel = new ScoreGridPanel();
      }
      return scorePanel;
   }

   @Override
   public void onScreenSwitch(ScreenSwitchEvent p) {
      refreshContent();
   }

}
