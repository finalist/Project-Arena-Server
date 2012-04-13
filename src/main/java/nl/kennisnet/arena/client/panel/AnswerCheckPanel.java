package nl.kennisnet.arena.client.panel;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.AnswerDTO;
import nl.kennisnet.arena.client.domain.AnswerDTO.Result;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A panel where the user can check for answers
 * 
 * @author A. Egal
 */
public class AnswerCheckPanel extends SimplePanel implements
		RefreshQuestLogEvent.Handler, ResizablePanel {

	private Panel panel = new FlowPanel();

	public AnswerCheckPanel() {
		buildUnansweredQuestionPanel();
		panel.setStyleName("answer-page-view");
		this.add(panel);
	}

	private void buildUnansweredQuestionPanel() {
		List<AnswerDTO> answers = filterUncheckedQuestions();
		for (AnswerDTO answerDTO : answers) {
			showAnswer(answerDTO);
		}
		if(answers.size() == 0){
			VerticalPanel vp = new VerticalPanel();
			vp.setStyleName("answer-page-view");
			Label label = new Label("Er zijn nog geen open vragen beantwoord");
			label.setStyleName("question-view");
			vp.add(label);
			panel.add(vp);
		}
	}

	private void showAnswer(AnswerDTO answerDTO) {
		Panel questionContainer = new VerticalPanel();
		questionContainer.setStyleName("question-container-view");
				
		questionContainer.add(buildQuestion(answerDTO));

		Panel AnswerContainer = new VerticalPanel()	;	
		AnswerContainer.setStyleName("answer-container-view");
		
		AnswerContainer.add(buildTeamName(answerDTO));
				
		AnswerContainer.add(buildAnswer(answerDTO));
		
		AnswerContainer.add(addButtons(answerDTO));
		
		panel.add(questionContainer);
		panel.add(AnswerContainer);
		
	}
	
	/**
	 * Sorts unchecked answered questions to the top
	 * @return
	 */
	public List<AnswerDTO> filterUncheckedQuestions(){
		List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
		List<AnswerDTO> answers = QuestState.getInstance().getAnswers();
		
		for(AnswerDTO answerDTO : answers){
			if(answerDTO.getResult().equals(Result.ANSWERED.name())){
				answerDTOs.add(answerDTO);
			}
		}
		return answerDTOs;
	}

	@Override
	public void resize(int x, int y) {
		panel.setHeight(y + "px");
		panel.setWidth(x + "px");

	}

	@Override
	public void onRefreshQuestLog(RefreshQuestLogEvent p) {
		// TODO Auto-generated method stub

	}
	
	private Label buildQuestion(AnswerDTO answerDTO){
		Label question = new Label(answerDTO.getQuestionName() + ": "+ answerDTO.getQuestionDescription());		
		question.setStyleName("question-view");
		return question;
	}
	
	private Panel buildTeamName(AnswerDTO answerDTO){
		Panel team = new HorizontalPanel(); 
		Label teamname = new Label("Is beantwoord door team: "+answerDTO.getPlayerName());		
		team.setStyleName("answer-view");
		
		FlowPanel fp = new FlowPanel();
		fp.setStyleName(answerDTO.getPlayerName());
		fp.setWidth("20px");
		fp.setHeight("20px");
		
		team.add(teamname);
		team.add(fp);
		return team;
	}
	
	private Panel buildAnswer(AnswerDTO answerDTO){
		Panel answerPanel = new HorizontalPanel(); 
		Label answerLabel = new Label("Antwoord: ");
		Label answer = new Label(answerDTO.getAnswer());
		
		answerPanel.setStyleName("answer-view");
		answer.setStyleName("answer");
		
		answerPanel.add(answerLabel);
		answerPanel.add(answer);
		
		return answerPanel;
	}
	
	private Panel addButtons(final AnswerDTO answerDTO){
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("answer-view");
		Button correct = new Button("Correct");
		correct.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				answerDTO.setResult(AnswerDTO.Result.CORRECT.name());
				save(answerDTO);
			}
		});
		Button incorrect = new Button("Incorrect");
		incorrect.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				answerDTO.setResult(AnswerDTO.Result.INCORRECT.name());
				save(answerDTO);
			}
		});
		
		hp.add(correct);
		hp.add(incorrect);
		return hp;
	}

	private void save(AnswerDTO answerDto){
		GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
        questService.update(answerDto,  new AsyncCallback<AnswerDTO>() {
           @Override
           public void onSuccess(AnswerDTO arg0) {
//              com.google.gwt.user.client.Window.alert("Saving of Areana succeded!");
              panel.clear();
              buildUnansweredQuestionPanel();
           }

           @Override
           public void onFailure(Throwable arg0) {
              Window.alert("Er heeft zich een fout voorgedaan bij het toevoegen van het antwoord: "+arg0.getMessage());
           }
        });
	}
}
