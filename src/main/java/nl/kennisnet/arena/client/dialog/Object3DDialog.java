package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.event.TeamFilterEvent;
import nl.kennisnet.arena.client.widget.ExtendedTextBox;
import nl.kennisnet.arena.client.widget.FormTablePanel;

import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;

public class Object3DDialog extends QuestItemDialog {

	private ExtendedTextBox storyTextBox;
	private TextBox[] settings;

	private static int MAX_CHARACTERS = 160;

	public Object3DDialog(QuestItemDTO itemDTO, boolean readOnlyDialog,
			boolean create) {
		super(itemDTO, readOnlyDialog, create);
		setText("3D Object details");
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		result.add(createNamePanel());
		result.add(createStoryPanel("3D Object"));
		result.addAll(createSettingsPanel());
		result.add(createRadiusPanel());
		result.add(createVisibleRadiusPanel());
		return result;
	}

	protected List<FormTablePanel.Element> createSettingsPanel() {
		List<FormTablePanel.Element> items = new ArrayList<FormTablePanel.Element>();
		
		settings = new TextBox[3];
		settings[0] = new TextBox();
		settings[1] = new TextBox();
		settings[2] = new TextBox();
		
		settings[0].setName("Schaal");
		settings[1].setName("Rotatie");
		settings[2].setName("Altitude");
		
		for(TextBox t : settings) {
			items.add(new FormTablePanel.Element(t.getName(), t));	
		}
		
		items.add(new FormTablePanel.Element("Geblendeerd", new RadioButton("Blended")));
		
		return items;
	}

	protected FormTablePanel.Element createStoryPanel(String title) {
		storyTextBox = new ExtendedTextBox(80, 4, MAX_CHARACTERS);
		return new FormTablePanel.Element(title, storyTextBox);
	}

	protected void fillFormFromItem(QuestItemDTO itemDTO) {
		super.fillFormFromItem(itemDTO);
		storyTextBox.setText(itemDTO.getDescription());
	}

	protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO) {
		QuestItemDTO result = super.fillItemFromForm(itemDTO);
		result.setDescription(storyTextBox.getText());
		return result;
	}

}
