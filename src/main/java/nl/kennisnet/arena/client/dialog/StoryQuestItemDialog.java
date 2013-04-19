package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.elements.StoryElement;
import nl.kennisnet.arena.client.widget.ExtendedTextBox;
import nl.kennisnet.arena.client.widget.FormTablePanel;
import nl.kennisnet.arena.model.ContentElement;

public class StoryQuestItemDialog extends QuestItemDialog {

	private ExtendedTextBox storyTextBox;
	private StoryElement element;
	
	private static int MAX_CHARACTERS = 160;

	public StoryQuestItemDialog(PoiDTO itemDTO, StoryElement element, boolean readOnlyDialog,
			boolean create) {
		super(itemDTO, readOnlyDialog, create);
		this.element = element;
		if(element != null) {
			fillFormFromItem(element);
		}
		setText("Verhaal details");
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		// result.add(createNamePanel());
		result.add(createStoryPanel("Verhaal"));
		// result.add(createRadiusPanel());
		// result.add(createVisibleRadiusPanel());
		return result;
	}

	protected FormTablePanel.Element createStoryPanel(String title) {
		storyTextBox = new ExtendedTextBox(80, 4, MAX_CHARACTERS);
		return new FormTablePanel.Element(title, storyTextBox);
	}

	protected void fillFormFromItem(StoryElement element) {
		//super.fillFormFromItem(itemDTO);
		storyTextBox.setText(element.getDescription());
	}

	protected PoiDTO fillItemFromForm(PoiDTO itemDTO) {
		PoiDTO result = super.fillItemFromForm(itemDTO);
		result.getElements().add(new StoryElement(storyTextBox.getText()));
		//result.setDescription(storyTextBox.getText());
		return result;
	}

}
