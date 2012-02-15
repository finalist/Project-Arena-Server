package nl.kennisnet.arena.formats.convert;

import static nl.kennisnet.arena.model.PositionableCollectionHelper.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.kennisnet.arena.client.domain.ActionDTO;
import nl.kennisnet.arena.client.domain.QuestState;
import nl.kennisnet.arena.formats.Asset;
import nl.kennisnet.arena.formats.Dimension;
import nl.kennisnet.arena.formats.FeatureImg;
import nl.kennisnet.arena.formats.FeatureTxt;
import nl.kennisnet.arena.formats.OverlayImg;
import nl.kennisnet.arena.formats.OverlayTxt;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Progress;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.utils.ConvertorConfiguration;
import nl.kennisnet.arena.utils.GamarayDataBean;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.configuration.CompositeConfiguration;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public final class DimensionFactory {

	private static final String WALL_TEXT = "Hier mag je niet verder, ga terug!";

	private DimensionFactory() { // no instances allowed
	}

	/**
	 * Returns a general dimension instance without features
	 * 
	 * @param quest
	 * @return
	 */
	public static Dimension getEmptyInstance(final Quest quest, final GamarayDataBean data, final CompositeConfiguration configuration) {
		// final ConvertorConfiguration config = new
		// ConvertorConfiguration(data, configuration, quest);

		final List<FeatureTxt> positionableFeaturesTxt = Collections.emptyList();
		final List<OverlayTxt> positionableOverlayTxt = Collections.emptyList();
		final List<FeatureImg> positionableFeaturesImg = Collections.emptyList();
		final List<Asset> assets = Collections.emptyList();

		return new Dimension(quest.getName(), UtilityHelper.url(quest, data.getPlayer(), configuration), positionableFeaturesTxt, positionableFeaturesImg, assets,
				positionableOverlayTxt, null);
	}

	/**
	 * Returns a specific dimension instance where lat+lon filtering was applied
	 * 
	 * @param quest
	 * @return
	 */
	public static Dimension getInstance(final Quest quest, final GamarayDataBean data,
			final CompositeConfiguration configuration, final Progress progress, final  Map<MultiKey, Integer> answers, final Long participantId) {
		final ConvertorConfiguration config = new ConvertorConfiguration(data, configuration, quest);

		final InformationConvertor informationConvertor = new InformationConvertor();
		final QuestionConvertor questionConvertor = new QuestionConvertor(config,answers,participantId);
		final ImageConvertor imageConvertor = new ImageConvertor();
		final ImageToAssetConvertor imageToAssetConvertor = new ImageToAssetConvertor();

		final List<FeatureTxt> positionableFeaturesTxt = new ArrayList<FeatureTxt>();
		final List<OverlayTxt> positionableOverlayTxt = new ArrayList<OverlayTxt>();
		final List<OverlayImg> positionableOverlayImg = new ArrayList<OverlayImg>();
		
		final List<FeatureImg> positionableFeaturesImg = new ArrayList<FeatureImg>();
		final List<Asset> assets = new ArrayList<Asset>();

		if (isInBorder(data.getLocation(), quest.getBorder())) {
			
			
			//TODO : Show the radar positionables.
         final List<Positionable> radarPositionables = quest.getPositionablesForRadar(data.getLocation());
         positionableFeaturesTxt.addAll(Lists.transform(filter(radarPositionables, Positionable.class).all(), new PositionableRadarConvertor()));
			
			//TODO : Show the hotzone features.
         final List<Positionable> visiblePositionables = quest.getVisiblePositionables(data.getLocation());

			final List<Image> images = filter(visiblePositionables, Image.class).all();
			assets.addAll(Lists.transform(images, imageToAssetConvertor));
			positionableFeaturesImg.addAll(Lists.transform(images, imageConvertor));
			
			positionableFeaturesTxt.addAll(Lists.transform(filter(visiblePositionables, Information.class).all(), informationConvertor));

			final List<List<OverlayTxt>> questionsAndAnswers = Lists.transform(filter(visiblePositionables, Question.class).all(), questionConvertor);
			for (final List<OverlayTxt> questionAndAnswers : questionsAndAnswers) {
				positionableOverlayTxt.addAll(questionAndAnswers);
			}
			
		} else {
			// Hit a wall!
			final OverlayTxt wallOverlay = new OverlayTxt("wall", 120, 50, "TL", null, WALL_TEXT, 300);
			positionableOverlayTxt.add(wallOverlay);
		}

		final OverlayTxt scoreOverlay = new OverlayTxt("score", 370, 20, "TL", null, formatProgress(progress), 100);
		positionableOverlayTxt.add(scoreOverlay);

		return new Dimension(quest.getName(), UtilityHelper.url(quest, data.getPlayer(), configuration), positionableFeaturesTxt,
				positionableFeaturesImg, assets, positionableOverlayTxt, positionableOverlayImg);
	}

	private static String formatProgress(final Progress progress) {
		return String.format("Score: %d/%d", progress.getCurr(), progress.getMax());
	}

	/**
	 * Calculates if the current position is within the border. Always returns
	 * true if there is no border specified.
	 * 
	 * @param location
	 * @param border
	 * @return
	 */
	private static boolean isInBorder(final Point location, final Polygon border) {
		if (border == null) {
			return true;
		}
		return location.within(border);
	}
	
}