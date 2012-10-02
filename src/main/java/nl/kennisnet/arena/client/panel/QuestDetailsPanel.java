package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;
import nl.kennisnet.arena.client.widget.FormTablePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuestDetailsPanel extends SidePanel implements
		RefreshQuestEvent.Handler {

	private TextBox questNameBox;
	private TextBox designerBox;
	private Panel content;
	private FormTablePanel formPanel;

	public QuestDetailsPanel(boolean readOnly) {
		super("ARena", "Vul hier de gegevens van de ARena in.", false);
		EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
		content = new SimplePanel();
		createFormPanel();
		content.add(formPanel);
		this.add(content);
		if (readOnly) {
			questNameBox.setReadOnly(true);
			designerBox.setReadOnly(true);
		} else {
			this.setHorizontalAlignment(ALIGN_CENTER);
			this.add(createSavePanel());
		}
		refreshValues();

	}

	private void createFormPanel() {
		formPanel = new FormTablePanel();
		designerBox = new TextBox();
		questNameBox = new TextBox();
		formPanel.addField(new Label("naam"), questNameBox);
		formPanel.addField(new Label("email"), designerBox);
	}

	private void refreshValues() {
		QuestDTO questDTO = QuestState.getInstance().getState();
		if (questDTO != null) {
			questNameBox.setValue(questDTO.getName());
			designerBox.setValue(questDTO.getEmailOwner());
		}
	}

	private Panel createSavePanel() {
		SimplePanel result = new SimplePanel();
		final String questId = com.google.gwt.user.client.Window.Location
				.getParameter("arenaId");
		if (questId == null) {
			result.add(createSaveButton());
		} else {
			result.add(createUpdateButtom());
		}
		return result;
	}

	private void showProgressIndicator() {
		content.clear();
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");
		panel.setHorizontalAlignment(ALIGN_CENTER);
		panel.add(new Image("loading2.gif"));
		content.add(panel);
	}

	private void showPanel() {
		content.clear();
		content.add(formPanel);
	}

	@Override
	public void onRefreshQuest(RefreshQuestEvent p) {
		refreshValues();
	}

	private void fillItemFromForm() {
		QuestState.getInstance().getState().setName(questNameBox.getValue());
		QuestState.getInstance().getState()
				.setEmailOwner(designerBox.getValue());
	}

	private Button createSaveButton() {
		return new Button("Bewaar en stuur een bevestigingsmail",
				saveButtonClick(true));
	}

	private Widget createUpdateButtom() {
		return new Button("Aanpassen", saveButtonClick(true));
	}

	private ClickHandler saveButtonClick(final boolean sendNotification) {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				showProgressIndicator();
				fillItemFromForm();
				GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT
						.create(GWTQuestService.class);
				
				questService.save(QuestState.getInstance().getState(),
						sendNotification, new AsyncCallback<QuestDTO>() {
					
					
							@Override
							public void onSuccess(QuestDTO arg0) {
								// com.google.gwt.user.client.Window.alert("Saving of Areana succeded!");
							System.out.println(arg0.getEmailOwner());
							System.out.println(arg0.getItems().size());
								QuestState.getInstance().setState(arg0);
								EventBus.get().fireEvent(
										new RefreshQuestEvent());
								showPanel();
							}

							@Override
							public void onFailure(Throwable arg0) {
								Window.alert("Er heeft zich een fout voorgedaan bij het opslaan van de Arena :"
										+ arg0.getMessage());
								showPanel();
							}
						});
			}

		};
	}

}
