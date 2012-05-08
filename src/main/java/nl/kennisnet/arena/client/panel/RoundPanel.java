package nl.kennisnet.arena.client.panel;

import static com.google.gwt.user.client.Window.alert;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.client.service.GWTQuestServiceAsync;
import nl.kennisnet.arena.client.widget.FormTablePanel;
import nl.kennisnet.arena.client.widget.SingleSelectList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creates, views, updates, and deletes the (game)Rounds of the quest.
 * 
 * @author A. Egal
 */
public class RoundPanel extends SidePanel implements RefreshQuestEvent.Handler {

	private final Panel itemGrid;
	private SingleSelectList tools;
	private final Panel scrollPanel;

	public RoundPanel() {
		super("Rondes", "Selecteer hier de speelrondes", false);
		EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);

		itemGrid = new VerticalPanel();
		scrollPanel = new ScrollPanel(itemGrid);
		add(scrollPanel);
		setHorizontalAlignment(ALIGN_CENTER);
		refresh();
	}

	public Widget createAddRoundButtonWidget() {
		Button button = new Button("Voeg ronde toe");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createRoundDialog();
			}
		});
		return button;
	}

	public Widget createDeleteRoundButtonWidget(){
		Button button = new Button("Verwijder geselecteerde ronde");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(QuestState.getInstance().getState().getRounds().size()  > 1){
					deleteRound((RoundDTO)tools.getValue());
				} else{
					alert("De ronde kan niet verwijderd worden, er moet minimaal 1 ronde overblijven.");
				}
			}
		});
		return button;		
	}
	
	private void createRoundDialog() {
		final DialogBox db = new DialogBox();
		final VerticalPanel vp = new VerticalPanel();
		final TextBox tb = new TextBox();

		FormTablePanel formPanel = new FormTablePanel();
		formPanel.addField(new FormTablePanel.Element("Ronde naam", tb));
		vp.add(formPanel);

		final HorizontalPanel hp = new HorizontalPanel();
		final Button ok = new Button("Voeg toe");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				saveRound(new RoundDTO(tb.getText()));
				db.removeFromParent();
			}
		});
		hp.add(ok);

		final Button anuleren = new Button("Anuleren");
		anuleren.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				db.removeFromParent();
			}
		});
		hp.add(anuleren);

		vp.add(hp);
		db.add(vp);
		db.center();
	}

	private void saveRound(RoundDTO round){
		QuestState.getInstance().getState().getRounds().add(round);
		saveQuest();
	}
	
	private void deleteRound(RoundDTO round){
		QuestState.getInstance().getState().getRounds().remove(round);
		saveQuest();
	}
	
	private void saveQuest(){
        GWTQuestServiceAsync questService = (GWTQuestServiceAsync) GWT.create(GWTQuestService.class);
		questService.save(QuestState.getInstance().getState(), false, new AsyncCallback<QuestDTO>() {
            @Override
            public void onSuccess(QuestDTO arg0) {
               QuestState.getInstance().setState(arg0);
               EventBus.get().fireEvent(new RefreshQuestEvent());
               refresh();
            }

            @Override
            public void onFailure(Throwable arg0) {
               Window.alert("Er heeft zich een fout voorgedaan bij het toevoegen van een ronde :"+arg0.getMessage());
               refresh();
            }
         });
	}
	
	@Override
	public void onRefreshQuest(RefreshQuestEvent p) {
		refresh();
	}

	private void refresh() {
		itemGrid.clear();
		this.getElement().setId("typeselect");
		itemGrid.setStyleName("rounds_panel");
		QuestDTO quest = QuestState.getInstance().getState();
		HorizontalPanel hp = new HorizontalPanel();
		if (quest != null && quest.getRounds() != null && quest.getRounds().size() > 0) {
			tools = new SingleSelectList();
			for (RoundDTO roundDto : quest.getRounds()) {
				tools.addOption(roundDto, roundDto.getName());
			}
			tools.setValue(quest.getActiveRound(),false);
			itemGrid.add(tools);
			hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		hp.add(createAddRoundButtonWidget());
		hp.add(createDeleteRoundButtonWidget());
		itemGrid.add(hp);
		startOnChangeRoundsListHandler();
	}
	
	private void startOnChangeRoundsListHandler(){
		tools.addValueChangeHandler(new ValueChangeHandler<Object>() {
	         
	         @Override
	         public void onValueChange(ValueChangeEvent<Object> event) {
	            if (tools.getValue()!=null){
	               QuestState.getInstance().getState().setActiveRound((RoundDTO)tools.getValue());
	               EventBus.get().fireEvent(new RefreshQuestEvent());
	               EventBus.get().fireEvent(new RefreshQuestLogEvent());
	               saveQuest();
	            }
	         }
	      });
	}

}
