package nl.kennisnet.arena.formats.convert;

import java.util.List;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.formats.Asset;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.configuration.CompositeConfiguration;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;

public class AnswerToAssetConvertor implements Function<Question, Asset> {

	private final List<ActionDTO> questionLogs;
	private final CompositeConfiguration configuration;

	public AnswerToAssetConvertor(List<ActionDTO> questionLogs,
			CompositeConfiguration configuration) {
		super();
		this.questionLogs = questionLogs;
		this.configuration = configuration;
	}

	public Asset apply(Question q) {
		String url = String.format("%sarrow_right.png", UtilityHelper
				.getBaseUrl(configuration));

		for (ActionDTO questionLog : questionLogs) {
			if (q.getId().equals(questionLog.getPositionableId())) {
				if (StringUtils.hasText(questionLog.getAnswer())) {
					Asset asset = new Asset(String
							.format("asset_%d", q.getId()), "PNG", url);
					return asset;
				}
			}
		}

		return null;
	}
}
