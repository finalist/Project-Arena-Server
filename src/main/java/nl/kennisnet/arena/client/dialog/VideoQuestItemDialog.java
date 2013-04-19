package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.widget.FormTablePanel;
import nl.kennisnet.arena.model.ContentElement;

import nl.kennisnet.arena.client.elements.VideoElement;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VideoQuestItemDialog extends QuestItemDialog {

	private TextBox videoTextBox;
	private VideoElement element;

	public VideoQuestItemDialog(PoiDTO itemDTO, VideoElement element,
			boolean readOnlyDialog, boolean create) {
		super(itemDTO, readOnlyDialog, create);
		this.element = element;
		if (element != null) {
			fillFormFromItem(element);
		}
		setText("Video details");
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		// result.add(createNamePanel());
		result.add(createVideoPanel("Video url (youtube)",
				"(in de formaat: http://www.youtube.com/watch?v=8tPnX7OPo0Q )"));
		// result.add(createRadiusPanel());
		// result.add(createVisibleRadiusPanel());
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

	protected void fillFormFromItem(VideoElement element) {
		//super.fillFormFromItem(itemDTO);
		videoTextBox.setText(element.getUrl());
	}

	protected PoiDTO fillItemFromForm(PoiDTO itemDTO) {
		PoiDTO result = super.fillItemFromForm(itemDTO);
		result.getElements().add(new VideoElement(videoTextBox.getText()));
		// result.setObjectURL(videoTextBox.getText());
		return result;
	}

}
