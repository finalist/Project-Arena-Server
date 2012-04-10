package nl.kennisnet.arena.client.service;

import java.util.List;

import nl.kennisnet.arena.client.domain.AnswerDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTQuestServiceAsync {

    void load(Long id, AsyncCallback<QuestDTO> callback);
   
    void save(QuestDTO quest, AsyncCallback<QuestDTO> callback);
  
    void getLog(Long questId,AsyncCallback<LogDTO> callback );

    void clearLog(Long id,AsyncCallback<LogDTO> callback);

    void getAnswer(Long questId,AsyncCallback<List<AnswerDTO>> callback);

	void update(AnswerDTO answerDto, AsyncCallback<AnswerDTO> callback);
}
