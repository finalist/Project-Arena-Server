package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO.TYPE;
import nl.kennisnet.arena.client.widget.FormTablePanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class QuestionQuestItemDialog extends StoryQuestItemDialog {

	private TextBox[] answersTextBoxs;
	private RadioButton[] correctAnswer;
	private RadioButton[] questionType;
	
	private List<FormTablePanel.Element> multipleChoiceElements;

	public QuestionQuestItemDialog(final QuestItemDTO itemDTO,
			boolean readOnlyDialog, boolean create) {
		super(itemDTO, readOnlyDialog, create);
		setText("Vraag details");
	}

	private List<FormTablePanel.Element> createAnswerPanels() {
		multipleChoiceElements = new ArrayList<FormTablePanel.Element>();
		answersTextBoxs = new TextBox[4];
		correctAnswer = new RadioButton[4];
		for (int i = 0; i < 4; i++) {
			Panel panel = new HorizontalPanel();
			answersTextBoxs[i] = new TextBox();
			answersTextBoxs[i].setMaxLength(80);
			panel.add(answersTextBoxs[i]);
			correctAnswer[i] = new RadioButton("correctAnswer");
			panel.add(correctAnswer[i]);
			multipleChoiceElements.add(new FormTablePanel.Element("Antwoord " + (i + 1), panel));
		}
		return multipleChoiceElements;
	}

	private FormTablePanel.Element createQuestionTypePanel() {

		Panel panel = new HorizontalPanel();
		questionType = new RadioButton[2];

		panel.add(createMulitpleChoiceRadioButtion(true));
		panel.add(new Label("Multiple choice"));

		Panel panel2 = new HorizontalPanel();
		panel2.add(createOpenQuestionRadioButtion(false));
		panel2.add(new Label("Open vraag"));

		Panel verPanel = new VerticalPanel();
		verPanel.add(panel);
		verPanel.add(panel2);

		return new FormTablePanel.Element("Type", verPanel);
	}

	private RadioButton createMulitpleChoiceRadioButtion(boolean visible) {
		questionType[0] = new RadioButton("questionType");
		questionType[0].setValue(visible);
		questionType[0].addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				toggleMultipleChoiceElementsVisibility(true);
			}
		});
		return questionType[0];
	}
	
	private RadioButton createOpenQuestionRadioButtion(boolean visible){
		questionType[1] = new RadioButton("questionType");
		questionType[1].setValue(visible);
		questionType[1].addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				toggleMultipleChoiceElementsVisibility(false);
			}
		});		
		return questionType[1];
	}
	
	private void toggleMultipleChoiceElementsVisibility(Boolean visible){
		for(FormTablePanel.Element element: multipleChoiceElements){
			element.getLabel().setVisible(visible);
			element.getField().setVisible(visible);
		}
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		result.add(createNamePanel());
		result.add(createQuestionTypePanel());
		result.add(createStoryPanel("Vraag"));
		result.addAll(createAnswerPanels());
		result.add(createRadiusPanel());
		return result;
	}

	protected void fillFormFromItem(QuestItemDTO itemDTO) {
		super.fillFormFromItem(itemDTO);
		if(itemDTO.getQuestionTypeAsEnum() == TYPE.MULTIPLE_CHOICE){
			answersTextBoxs[0].setText(itemDTO.getOption1());
			answersTextBoxs[1].setText(itemDTO.getOption2());
			answersTextBoxs[2].setText(itemDTO.getOption3());
			answersTextBoxs[3].setText(itemDTO.getOption4());
		} else if(itemDTO.getQuestionTypeAsEnum() == TYPE.OPEN_QUESTION){
			questionType[0].setValue(false);
			questionType[1].setValue(true);
			toggleMultipleChoiceElementsVisibility(false);
		}
		if (correctAnswer != null) {
			for (int i = 0; i < correctAnswer.length; i++) {
				RadioButton correct = correctAnswer[i];
				if (itemDTO.getCorrectOption() != null
						&& itemDTO.getCorrectOption() == i + 1
						&& correct != null) {
					correct.setValue(true);
				} else {
					correct.setValue(false);
				}

			}
		}
	}

	protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO) {
		QuestItemDTO result = super.fillItemFromForm(itemDTO);
		
		if(questionType[0].getValue() == true){
			itemDTO.setOption1(answersTextBoxs[0].getText());
			itemDTO.setOption2(answersTextBoxs[1].getText());
			itemDTO.setOption3(answersTextBoxs[2].getText());
			itemDTO.setOption4(answersTextBoxs[3].getText());
			itemDTO.setQuestionType(0);
		}else{
			itemDTO.setQuestionType(1);
		}		

		if (correctAnswer != null) {
			for (int i = 0; i < correctAnswer.length; i++) {
				RadioButton correct = correctAnswer[i];
				if (correct != null && correct.getValue()) {
					itemDTO.setCorrectOption(i + 1);
				}
			}
		}

		return result;
	}

}
