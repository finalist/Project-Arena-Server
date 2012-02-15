package nl.kennisnet.arena.client.service;

import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/questService")
public interface GWTQuestService extends RemoteService{
   
   QuestDTO load(Long id);
   QuestDTO save(QuestDTO quest);
   LogDTO getLog(Long questId);
   LogDTO clearLog(Long id);


}
