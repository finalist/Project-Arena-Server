package nl.kennisnet.arena.services;

import nl.kennisnet.arena.formats.Item;
import nl.kennisnet.arena.formats.convert.ItemFactory;
import nl.kennisnet.arena.model.ContentElement;
import nl.kennisnet.arena.model.Location;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.repository.QuestRepository;
import nl.kennisnet.arena.utils.UtilityHelper;

import org.apache.commons.configuration.CompositeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private CompositeConfiguration configuration;

    public Item getContentElement(Long questId, Long itemId, String player) {
        Quest quest = questRepository.get(questId);

        ContentElement element = getElementById(quest, itemId);
        if (element == null) {
            return null;
        }

        String baseUrl = UtilityHelper.getBaseUrl(configuration);
        String submitUrl = String.format("(%sitem/show/%s/%s/%s.item)", baseUrl, questId, itemId, player);

        return ItemFactory.getInstance(element, submitUrl);
    }

    private ContentElement getElementById(Quest quest, long itemId) {
        for (Location location : quest.getLocations()) {
            for (ContentElement element : location.getElements()) {
                if (element.getId() == itemId) {
                    return element;
                }
            }
        }
        return null;
    }

}
