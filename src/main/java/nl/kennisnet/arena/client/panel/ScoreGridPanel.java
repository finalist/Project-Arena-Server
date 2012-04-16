package nl.kennisnet.arena.client.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.client.domain.TeamDTO;
import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.RefreshQuestLogEvent;
import nl.kennisnet.arena.client.util.PrintUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author A. Egal A refractored LogTablePanel, used a grid instead of a
 *         flextable and used a AnswerDTO instead of a LogDTO, added open
 *         questions support
 */
public class ScoreGridPanel extends SimplePanel implements
		RefreshQuestLogEvent.Handler, ResizablePanel {

	private String orderTeams = ORDER_NAME;
	private String orderItems = ORDER_NAME;

	private final static String ORDER_NAME = "name";
	private final static String ORDER_SCORE = "score";

	private ScrollPanel container = new ScrollPanel();

	public ScoreGridPanel() {
		EventBus.get().addHandler(RefreshQuestLogEvent.TYPE, this);
		container = new ScrollPanel();
		add(container);
		fillGrid();
	}

	private void fillGrid() {
		container.clear();
		VerticalPanel logPanel = new VerticalPanel();
		logPanel.setWidth("100%");
		QuestDTO quest = QuestState.getInstance().getState();
		logPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		Label title = new Label("Score van Arena '" + quest.getName() + "' op "
				+ currentDate());
		title.setStyleName("scoreheader");
		logPanel.add(title);
		logPanel.add(createSortLinks());
		Grid grid = createScoreGrid();
		if (grid != null) {
			setStyle(grid);
			logPanel.add(grid);
		}
		logPanel.add(buildPrintButton(grid));
		container.add(logPanel);
	}

	private Grid createScoreGrid() {
		LogDTO log = QuestState.getInstance().getLog();
		QuestDTO quest = QuestState.getInstance().getState();
		Grid result = null;
		if (log != null) {
			result = new Grid();
			// result.setCellSpacing(0);
			result.addStyleName("FlexTable");
			setHTML(result, 0, 0, "Speld", "FlexTable-column-label");
			List<Long> itemIndex = new ArrayList<Long>();
			List<String> teamIndex = new ArrayList<String>();

			for (QuestItemDTO itemDTO : getItems(quest, log, orderItems)) {
				if (itemDTO.getTypeName().equals("Vraag")) {
					itemIndex.add(itemDTO.getId());
					setHTML(result, itemIndex.size(), 0, itemDTO.getName()
							+ " (" + itemDTO.getScore() + ")",
							"FlexTable-row-label");
				}
			}

			for (TeamDTO team : getTeams(log, orderTeams)) {
				teamIndex.add(team.getName());
				int column = teamIndex.size();
				setHTML(result, 0, column, team.getName(),
						"FlexTable-column-label");
				setHTML(result, itemIndex.size() + 1, column, team.getScore()
						.toString(), "FlexTable-value");
			}

			setHTML(result, itemIndex.size() + 1, 0, "Vragen juist beantwoord",
					"FlexTable-row-label");

			for (ActionDTO action : log.getTypeActions("Vraag")) {
				int x = itemIndex.indexOf(action.getPositionableId()) + 1;
				int y = teamIndex.indexOf(action.getTeamName()) + 1;
				setHTML(result, x, y, answerIntToLetter(action.getAnswer()),
						"FlexTable-value");
			}
		}
		return result;
	}
	
	private List<QuestItemDTO> getItems(QuestDTO quest, LogDTO log, String order) {

		Map<Long, QuestItemDTO> itemMap = new HashMap<Long, QuestItemDTO>();
		if (quest != null && quest.getItems() != null) {
			for (QuestItemDTO questItemDTO : quest.getItems()) {
				itemMap.put(questItemDTO.getId(), questItemDTO);
				questItemDTO.setScore(0);
			}
		}
		if (log != null && log.getQuestItems() != null) {
			for (QuestItemDTO questItemDTO : log.getQuestItems()) {
				itemMap.put(questItemDTO.getId(), questItemDTO);
			}
		}

		List<QuestItemDTO> result = new ArrayList<QuestItemDTO>(
				itemMap.values());
		if (order.equals(ORDER_NAME)) {
			Collections.sort(result, new Comparator<QuestItemDTO>() {

				@Override
				public int compare(QuestItemDTO o1, QuestItemDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}

			});
		} else {
			Collections.sort(result, new Comparator<QuestItemDTO>() {

				@Override
				public int compare(QuestItemDTO o1, QuestItemDTO o2) {
					return -o1.getScore().compareTo(o2.getScore());
				}
			});

		}
		return result;
	}

	private List<TeamDTO> getTeams(LogDTO log, String order) {
		List<TeamDTO> result = new ArrayList<TeamDTO>(log.getTeams());
		if (order.equals(ORDER_NAME)) {
			Collections.sort(result, new Comparator<TeamDTO>() {

				@Override
				public int compare(TeamDTO o1, TeamDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}

			});
		} else {
			Collections.sort(result, new Comparator<TeamDTO>() {

				@Override
				public int compare(TeamDTO o1, TeamDTO o2) {
					return -o1.getScore().compareTo(o2.getScore());
				}
			});

		}
		return result;
	}

	@Override
	public void resize(int x, int y) {
		container.setHeight(y + "px");
		container.setWidth(x + "px");
	}

	private Panel createSortLinks() {
		VerticalPanel result = new VerticalPanel();
		result.add(createQuestItemSortLinks());
		result.add(createTeamSortLinks());
		return result;
	}

	private Panel createTeamSortLinks() {
		HorizontalPanel result = new HorizontalPanel();
		result.add(new Label("Sorteer teams op "));

		Anchor teamNameSort = new Anchor("naam");
		teamNameSort.addStyleName("sortlink");
		teamNameSort.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				orderTeams = ORDER_NAME;
				refresh();
			}
		});
		result.add(teamNameSort);

		result.add(new Label(" | "));

		Anchor teamScoreSort = new Anchor("score");
		teamScoreSort.addStyleName("sortlink");
		teamScoreSort.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				orderTeams = ORDER_SCORE;
				refresh();
			}
		});
		result.add(teamScoreSort);
		return result;
	}

	private void setStyle(Grid grid) {
		HTMLTable.RowFormatter rf = grid.getRowFormatter();

		for (int x = 0; x < grid.getRowCount(); x++) {
			for (int y = 0; y < grid.getCellCount(0); y++) {
				if (y >= grid.getCellCount(x) || grid.getWidget(x, y) == null
						|| !(grid.getWidget(x, y) instanceof Label)) {
					setHTML(grid, x, y, "-", "FlexTable-value");
				}
			}
		}

		for (int row = 1; row < grid.getRowCount(); ++row) {
			if ((row % 2) != 0) {
				rf.addStyleName(row, "FlexTable-OddRow");
			} else {
				rf.addStyleName(row, "FlexTable-EvenRow");
			}
		}
	}

	private void setHTML(Grid grid, Integer x, Integer y, String text,
			String style) {
		// EventBus.get().fireEvent(new LogEvent("Setting text " + text +
		// " to table at (" + x + "," + y + ")"));
		resizeGrid(grid, x, y);
		Widget widget = new Label(text);
		widget.addStyleName(style);
		grid.addStyleName("Grid-Cell");
		// widget.setWidth("100%");
		grid.setWidget(x, y, widget);
	}

	private void resizeGrid(Grid grid, Integer x, Integer y) {
		if(grid.getRowCount() <= x){
			grid.resizeRows(x+1);
		}
		if(grid.getColumnCount() <= y){
			grid.resizeColumns(y+1);
		}
		
	}

	private Panel createQuestItemSortLinks() {
		HorizontalPanel result = new HorizontalPanel();
		result.add(new Label("Sorteer spelden op "));
		Anchor itemNameSort = new Anchor("naam");
		itemNameSort.setStyleName("sortlink");
		itemNameSort.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				orderItems = ORDER_NAME;
				refresh();
			}
		});
		result.add(itemNameSort);
		result.add(new Label(" | "));
		Anchor itemScoreSort = new Anchor("score");
		itemScoreSort.setStyleName("sortlink");
		itemScoreSort.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				orderItems = ORDER_SCORE;
				refresh();
			}
		});
		result.add(itemScoreSort);
		return result;
	}

	private Widget buildPrintButton(final Grid grid) {
		Button button = new Button("Print");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				String style = "<link type=\"text/css\" rel=\"stylesheet\" href=\"../ARenaWA.css\">";
				PrintUtil.it(style, grid);
			}
		});
		return button;
	}

	@Override
	public void onRefreshQuestLog(RefreshQuestLogEvent p) {
		refresh();
	}

	private void refresh() {
		fillGrid();
	}

	private String currentDate() {
		DateTimeFormat format = DateTimeFormat
				.getFormat("EEEE dd MMMM yyyy  HH:mm");
		return format.format(new Date());
	}

	private String answerIntToLetter(String answer) {
		try {
			int q = Integer.parseInt(answer.trim());
			switch (q) {
			case 1:	return "A";
			case 2: return "B";
			case 3: return "C";
			case 4: return "D";
			default: return "";
			}
		} catch (NumberFormatException ne) {
			return answer;
		} catch (IndexOutOfBoundsException ie) {
			return answer;
		}
	}

}
