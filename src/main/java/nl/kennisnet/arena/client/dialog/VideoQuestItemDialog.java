package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.widget.FormTablePanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VideoQuestItemDialog extends QuestItemDialog {

	private TextBox videoTextBox;

	public VideoQuestItemDialog(QuestItemDTO itemDTO, boolean readOnlyDialog,
			boolean create) {
		super(itemDTO, readOnlyDialog, create);
		setText("Video details");
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		result.add(createNamePanel());
		result.add(createVideoPanel("Video url (youtube)", "(in de formaat: http://www.youtube.com/watch?v=8tPnX7OPo0Q )"));
		result.add(createRadiusPanel());
		result.add(createVisibleRadiusPanel());
		return result;
	}

	protected FormTablePanel.Element createVideoPanel(String title, String help) {
		VerticalPanel vp = new VerticalPanel();
		videoTextBox = new TextBox();
		Label l = new Label(help);
		vp.add(videoTextBox);
		vp.add(l);
		return new FormTablePanel.Element(title, vp);
	}

	protected void fillFormFromItem(QuestItemDTO itemDTO) {
		super.fillFormFromItem(itemDTO);
		videoTextBox.setText(itemDTO.getObjectURL());
	}

	protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO) {
		QuestItemDTO result = super.fillItemFromForm(itemDTO);
		result.setObjectURL(videoTextBox.getText());
		return result;
	}

}
