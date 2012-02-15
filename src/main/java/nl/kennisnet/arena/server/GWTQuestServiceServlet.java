package nl.kennisnet.arena.server;

import nl.kennisnet.arena.client.domain.LogDTO;
import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.service.GWTQuestService;
import nl.kennisnet.arena.services.ParticipantService;
import nl.kennisnet.arena.services.QuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service
public class GWTQuestServiceServlet extends RemoteServiceServlet implements GWTQuestService {

   private static final long serialVersionUID = 1L;
   
   // the RemoteServiceServlet implements Serializable. 
   // the QuestService however doesn't and due to instrumentation
   // by Spring SHOULDN'T be Serializable
   // -- pmaas
   private transient final QuestService questService;
   
   private transient final ParticipantService participantService;
   
   @Autowired
   public GWTQuestServiceServlet(final QuestService questService,final ParticipantService participantService) {
      this.questService = questService;
      this.participantService = participantService;
   }

   @Override
   public QuestDTO load(Long id) {
      return questService.getQuestDTO(id);
   }

   @Override
   public QuestDTO save(QuestDTO quest) {
      return questService.save(quest);

   }

   public LogDTO clearLog(Long id){
      participantService.clearQuestLog(id);
      return participantService.getParticipationLog(id);
   }
   
   @Override
   public LogDTO getLog(Long questId) {
      return participantService.getParticipationLog(questId);
   }
   
}
