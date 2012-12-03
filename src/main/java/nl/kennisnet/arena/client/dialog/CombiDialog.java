package nl.kennisnet.arena.client.dialog;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestItemTypes;
import nl.kennisnet.arena.client.widget.FormTablePanel;
import nl.kennisnet.arena.client.widget.MultiSelectList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class CombiDialog extends QuestItemDialog {

	private MultiSelectList items;

	public CombiDialog(QuestItemDTO itemDTO, boolean readOnlyDialog,
			boolean create) {
		super(itemDTO, readOnlyDialog, create);
		setText("Combi Object details");
	}

	@Override
	protected List<FormTablePanel.Element> createFormPanels() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();
		result.add(createNamePanel());
		result.add(createFilterPanel());
		result.add(createRadiusPanel());
		result.add(createVisibleRadiusPanel());
		return result;
	}

	protected void fillFormFromItem(QuestItemDTO itemDTO) {
		super.fillFormFromItem(itemDTO);

	}

	protected List<FormTablePanel.Element> createOptions() {
		List<FormTablePanel.Element> result = new ArrayList<FormTablePanel.Element>();

		for (final String t : QuestItemTypes.getTypes()) {
			HorizontalPanel panel = new HorizontalPanel();
			Button b = new Button();
			b.setText("Add");

			b.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					DialogSelector.showRelevantDialog(t, getQuestItemDTO(),
							false, true);

				}
			});

			panel.add(b);

			FormTablePanel.Element uh = new FormTablePanel.Element(t, panel);
			result.add(uh);
		}

		return result;
	}

	private FormTablePanel.Element createFilterPanel() {
		items = new MultiSelectList();
		HorizontalPanel hor = new HorizontalPanel();
		SimplePanel filterPanel = new SimplePanel();

		items.addOption(QuestItemTypes.QUEST_TYPE_STORY, "Verhaal (0)");
		items.addOption(QuestItemTypes.QUEST_TYPE_QUESTION, "Vraag (0)");
		items.addOption(QuestItemTypes.QUEST_TYPE_PHOTO, "Foto (0)");
		items.addOption(QuestItemTypes.QUEST_TYPE_VIDEO, "Video (0)");
		items.addOption(QuestItemTypes.QUEST_TYPE_3D, "3D Object (0)");
		items.addOption(QuestItemTypes.QUEST_TYPE_COMBI, "Gecombineerd (0)");
		items.addValueChangeHandler(new ValueChangeHandler<Object>() {

			@Override
			public void onValueChange(ValueChangeEvent<Object> event) {

			}

		});
		filterPanel.add(items);
		
		hor.setSpacing(30);
		hor.add(filterPanel);
		hor.add(filterPanel);
		
		return new FormTablePanel.Element("Items", hor);
	}

	protected QuestItemDTO fillItemFromForm(QuestItemDTO itemDTO) {
		QuestItemDTO result = super.fillItemFromForm(itemDTO);
		return null;
	}

	protected void refresh() {
		clear();
		setWidget(createPanel(getQuestItemDTO()));
		fillFormFromItem(getQuestItemDTO());
	}
}
