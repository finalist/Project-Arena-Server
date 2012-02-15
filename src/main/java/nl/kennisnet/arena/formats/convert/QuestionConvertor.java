package nl.kennisnet.arena.formats.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.kennisnet.arena.formats.OverlayTxt;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.utils.ConvertorConfiguration;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;

public class QuestionConvertor implements Function<Question, List<OverlayTxt>> {

   private final ConvertorConfiguration config;
   private final Map<MultiKey, Integer> answers;
   private final Long participantId;

   public QuestionConvertor(ConvertorConfiguration config, Map<MultiKey, Integer> answers, Long participantId) {
      this.config = config;
      this.answers = answers;
      this.participantId = participantId;
   }

   private static final int OVERLAY_MINIMUM_LEFT = 120;

   @Override
   public List<OverlayTxt> apply(Question q) {

      String serverUrl = UtilityHelper.url(config.getQuest(), config.getData().getPlayer(), config.getConfiguration());

      List<OverlayTxt> overlays = new ArrayList<OverlayTxt>();

      OverlayTxt question = new OverlayTxt(String.format("question_%d", q.getId()), OVERLAY_MINIMUM_LEFT, 50, "TL", null, q
            .getText(), 250);
      overlays.add(question);

      addAnswers(q, serverUrl, overlays);

      return overlays;
   }

   private void addAnswers(Question q, String serverUrl, List<OverlayTxt> overlays) {
      
      String[] answerText=new String[4];
      for (int i = 0; i < answerText.length; i++) {
         switch (i) {
         case 0: answerText[i]="[ ] "+q.getAnswer1(); break;
         case 1: answerText[i]="[ ] "+q.getAnswer2(); break;
         case 2: answerText[i]="[ ] "+q.getAnswer3(); break;
         case 3: answerText[i]="[ ] "+q.getAnswer4(); break;
         default:
            break;
         }
      }
      
      MultiKey teamItemKey = new MultiKey(q.getId(), participantId);
      Integer answer=answers.get(teamItemKey);
      if (answer!=null){
         answerText[answer-1]="[X] "+answerText[answer-1].substring(4);
      }
      
		OverlayTxt a1 = new OverlayTxt(answerId(1, q), OVERLAY_MINIMUM_LEFT, 140, "TL", answerUrl(serverUrl, answerId(1, q)), answerText[0], 250);
		overlays.add(a1);
		OverlayTxt a2 = new OverlayTxt(answerId(2, q), OVERLAY_MINIMUM_LEFT, 180, "TL", answerUrl(serverUrl, answerId(2, q)), answerText[1],
				250);
		overlays.add(a2);
		if (StringUtils.hasText(q.getAnswer3())) {
			OverlayTxt a3 = new OverlayTxt(answerId(3, q), OVERLAY_MINIMUM_LEFT, 220, "TL", answerUrl(serverUrl, answerId(3, q)),
			      answerText[2], 250);
			overlays.add(a3);
		}

		if (StringUtils.hasText(q.getAnswer4())) {
			OverlayTxt a4 = new OverlayTxt(answerId(4, q), OVERLAY_MINIMUM_LEFT, 260, "TL", answerUrl(serverUrl, answerId(4, q)),
			      answerText[3], 250);
			overlays.add(a4);
		}
	}

   private String answerId(int i, Question q) {
      return String.format("a%d_%d", i, q.getId());
   }

   private String answerUrl(String baseUrl, String answer) {
      return String.format("dimension: %s&answer=%s", baseUrl, answer);
   }
}
