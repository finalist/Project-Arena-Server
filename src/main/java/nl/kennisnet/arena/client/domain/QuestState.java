package nl.kennisnet.arena.client.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.LogEvent;

public class QuestState {

	public static final int DESIGNER_VIEW = 1;
	public static final int MONITOR_VIEW = 2;
	public static final int ANSWER_VIEW = 3;
	public static final int SCORE_VIEW = 4;

	private int currentView = DESIGNER_VIEW;

	private static final QuestState INSTANCE = new QuestState();
	private QuestDTO state;
	private Map<String, Integer> typeCounter = new HashMap<String, Integer>();
	private String selectedQuestType = QuestItemTypes.QUEST_TYPE_COMBI;
	private Set<String> hiddenTypes = new HashSet<String>();
	private Set<String> hiddenTeams = new HashSet<String>();
	private LogDTO log;
	private List<AnswerDTO> answers;

	public LogDTO getLog() {
		return log;
	}

	public void setLog(LogDTO log) {
		this.log = log;
	}

	public String getSelectedQuestType() {
		return selectedQuestType;
	}

	public void setSelectedQuestType(String selectedQuestType) {
		this.selectedQuestType = selectedQuestType;
	}

	// Private constructor prevents instantiation from other classes
	private QuestState() {
	}

	public static QuestState getInstance() {
		return INSTANCE;
	}

	public void setState(QuestDTO quest) {
		this.state = quest;
	}

	public QuestDTO getState() {
		return state;
	}

	public Integer getNumber(String itemType) {
		if (typeCounter.get(itemType) == null) {
			typeCounter.put(itemType, 1);
		}
		Integer result = typeCounter.get(itemType);
		typeCounter.put(itemType, result + 1);
		return result;
	}

	public void clearActionLog() {
		log = null;
	}

	public List<ActionDTO> getActionList(String teamName) {
		return log.getTeamActions(teamName);
	}

	public boolean isTeamVisible(String teamName) {
		boolean result = false;
		if (hiddenTeams != null) {
			result = !hiddenTeams.contains(teamName);
			EventBus.get().fireEvent(
					new LogEvent("team " + teamName + " is checked against :"
							+ hiddenTeams));
		}
		return result;
	}

	public void toggleTeamVisible(String teamName) {
		if (isTeamVisible(teamName)) {
			hiddenTeams.add(teamName);
			EventBus.get().fireEvent(
					new LogEvent("team " + teamName
							+ " is added to hidden teams :" + hiddenTeams));
		} else {
			hiddenTeams.remove(teamName);
			EventBus.get().fireEvent(
					new LogEvent("team " + teamName
							+ " is removed from hidden teams :" + hiddenTeams));
		}
	}

	public boolean isTypeVisible(String typeName) {
		boolean result = false;
		result = !hiddenTypes.contains(typeName);
		EventBus.get().fireEvent(
				new LogEvent("type " + typeName + " is checked against :"
						+ hiddenTeams));
		return result;
	}

	public void toggleTypeVisible(String typeName) {
		if (isTypeVisible(typeName)) {
			hiddenTypes.add(typeName);
			EventBus.get().fireEvent(
					new LogEvent("type " + typeName
							+ " is added to hidden types :" + hiddenTypes));
		} else {
			hiddenTypes.remove(typeName);
			EventBus.get().fireEvent(
					new LogEvent("type " + typeName
							+ " is removed from hidden types :" + hiddenTypes));
		}
	}

	public int getCurrentView() {
		return currentView;
	}

	public void setCurrentView(int currentView) {
		this.currentView = currentView;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

}
