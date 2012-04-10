package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestEvent;
import nl.kennisnet.arena.client.event.ScreenSwitchEvent;
import nl.kennisnet.arena.client.widget.SingleSelectList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class MenuPanel extends SimplePanel implements
		RefreshQuestEvent.Handler, ScreenSwitchEvent.Handler {

	private SingleSelectList menu;

	public MenuPanel() {
		super();
		this.getElement().setId("menu");
		EventBus.get().addHandler(RefreshQuestEvent.TYPE, this);
		EventBus.get().addHandler(ScreenSwitchEvent.TYPE, this);
		refreshMenuItems();
	}

	private void refreshMenuItems() {
		this.clear();
		menu = new SingleSelectList();
		menu.addOption(QuestState.DESIGNER_VIEW, "Designer");
		if (QuestState.getInstance().getState() != null
				&& QuestState.getInstance().getState().getId() != null) {
			menu.addOption(QuestState.MONITOR_VIEW, "Monitor");
			menu.addOption(QuestState.ANSWER_VIEW, "Antwoorden");
			menu.addOption(QuestState.SCORE_VIEW, "Scoreboard");
		}

		menu.addValueChangeHandler(new ValueChangeHandler<Object>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Object> event) {
				if (menu!=null&&menu.getValue()!=null){
					QuestState.getInstance().setCurrentView((Integer)menu.getValue());
					EventBus.get().fireEvent(new ScreenSwitchEvent((Integer)menu.getValue()));
				}
			}
		});
		syncActive();
		add(menu);
	}

	@Override
	public void onRefreshQuest(RefreshQuestEvent p) {
		refreshMenuItems();
	}

	private void syncActive() {
		if (menu != null) {
			menu.setValue(QuestState.getInstance().getCurrentView(),false);
		}
	}

	@Override
	public void onScreenSwitch(ScreenSwitchEvent p) {
		syncActive();
	}

}
