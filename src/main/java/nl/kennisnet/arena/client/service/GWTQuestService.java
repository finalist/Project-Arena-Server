package nl.kennisnet.arena.client.service;

import java.util.List;

import nl.kennisnet.arena.client.domain.AnswerDTO;
import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/questService")
public interface GWTQuestService extends RemoteService{
   
   QuestDTO load(Long id);
   QuestDTO save(QuestDTO quest);
   AnswerDTO update(AnswerDTO answerDto);
   LogDTO getLog(Long questId);
   LogDTO clearLog(Long id);
   List<AnswerDTO> getAnswer(Long questId);

}
