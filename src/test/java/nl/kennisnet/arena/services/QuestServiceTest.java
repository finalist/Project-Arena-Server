package nl.kennisnet.arena.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import geodb.GeoDB;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.PoiDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.client.elements.ImageElement;
import nl.kennisnet.arena.client.elements.QuestionElement;
import nl.kennisnet.arena.client.elements.StoryElement;
import nl.kennisnet.arena.model.Participant;
import nl.kennisnet.arena.model.Quest;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.repository.ParticipantRepository;
import nl.kennisnet.arena.repository.QuestRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/configuration.xml",
		"classpath:/integration.xml", "classpath:/integration-test.xml",
		"classpath:/services.xml", "classpath:arena-servlet-test.xml" })
@Transactional
public class QuestServiceTest {

	@Autowired
	private QuestService questService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private QuestRepository questRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private DataSource dataSource;

	private Long existingId;
	private QuestDTO existingQuest;
	private String existingName = "test-existing";
	private String existingEmail = "kns.arena.tester@gmail.com";
	private RoundDTO existingRound = new RoundDTO("test-round");

	@Before
	public void setUp() throws Exception {
		GeoDB.InitGeoDB(dataSource.getConnection());

		Participant participant = new Participant("henk");
		participant = participantRepository.merge(participant);

		QuestDTO questDTO = new QuestDTO();
		questDTO.setName(existingName);
		questDTO.setEmailOwner(existingEmail);
		PoiDTO itemDTO = new PoiDTO("testitem", "Verhaal");
		itemDTO.setPoint(new SimplePoint(2.2D, 1.1D));
		itemDTO.getElements().add(new StoryElement("Verhaaltje"));
		PoiDTO itemDTO2 = new PoiDTO("test", "Verhaal");
		itemDTO2.getElements().add(new ImageElement("Plaatje.jpg"));
		itemDTO2.setPoint(new SimplePoint(2.5D, 1.8D));
		List<PoiDTO> items = new ArrayList<PoiDTO>();
		items.add(itemDTO);
		items.add(itemDTO2);
		questDTO.setItems(items);
		questDTO.setActiveRound(existingRound);
		List<RoundDTO> rounds = new ArrayList<RoundDTO>();
		rounds.add(existingRound);
		questDTO.setRounds(rounds);
		existingQuest = questService.save(questDTO, true);
		existingId = existingQuest.getId();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewDesignSaveWithElements() {
		ImageElement img = new ImageElement("bla.nl");
		StoryElement story = new StoryElement("henk");
		
		PoiDTO dto = new PoiDTO("test", "combi");
		dto.setPoint(new SimplePoint(2.1D, 1.1D));
		dto.getElements().add(img);
		dto.getElements().add(story);
		
		QuestDTO quest = new QuestDTO();
		quest.setEmailOwner("hoi@hjoi.nl");
		quest.addItem(dto);
		
		assertThat(quest.getItems().get(0).getElements().size(), is(2));
		
		QuestDTO nieuw = questService.save(quest, false);
		
		assertThat(nieuw.getItems().size(), is(1));
		assertThat(nieuw.getItems().get(0).getElements().size(), is(2));
		
	}
	
	@Test
	public void testNewDesignSaveWithoutElements() {
		PoiDTO dto = new PoiDTO("test", "combi");
		dto.setPoint(new SimplePoint(2.1D, 1.1D));
		
		QuestDTO quest = new QuestDTO();
		quest.setEmailOwner("hoi@hjoi.nl");
		quest.addItem(dto);
		
		QuestDTO nieuw = questService.save(quest, false);
		
		Assert.assertNotNull(nieuw);
	}

	@Test
	public void testSave3DObject() {
		// Float[] rotation = new Float[3];
		// rotation[0] = 1f;
		// rotation[1] = 2f;
		// rotation[2] = 3f;
		//
		// QuestDTO questDTO = new QuestDTO();
		// questDTO.setEmailOwner("henk@henk.nl");
		// PoiDTO itemDTO = new PoiDTO("testObject", "Object3D");
		// itemDTO.setPoint(new SimplePoint(2.5D, 1.8D));
		// itemDTO.setBlended(1);
		// itemDTO.setAlt(5.0);
		// itemDTO.setRotation(rotation);
		// itemDTO.setSchaal(10f);
		//
		// questDTO.addItem(itemDTO);
		//
		// QuestDTO saved = questService.save(questDTO, true);
		//
		// assertThat(saved.getItems().size(), is(1));
		//
		// QuestDTO loaded = questService.getQuestDTO(saved.getId());
		//
		// assertThat(loaded.getItems().size(), is(1));
	}

	@Test
	public void when_saving_with_a_different_email_address_the_quest_items_should_be_cloned() {
		QuestDTO questDTO = questService.getQuestDTO(existingId);
		questDTO.setEmailOwner("jacques@finalist.com");
		QuestDTO newQuestDTO = questService.save(questDTO, true);
		assertThat(questDTO.getItems().get(0).getId(), is(not(newQuestDTO
				.getItems().get(0).getId())));
	}

	@Test
	public void when_saving_with_a_different_email_address_the_quest_items_should_not_be_removed_from_the_existing_quest() {
		QuestDTO questDTO = questService.getQuestDTO(existingId);
		questDTO.setEmailOwner("jacques@finalist.com");
		questService.save(questDTO, true);
		Quest oldQuest = questService.getQuest(questDTO.getId());
		assertThat(oldQuest.getPositionables().get(0).getQuest().getId(),
				is(oldQuest.getId()));
	}

	@Test
	@Rollback(true)
	public void when_saving_with_different_email_and_different_items_the_original_items_should_stay() {
		QuestDTO questDTO = questService.getQuestDTO(existingId);

		List<PoiDTO> items = questDTO.getItems();
		questDTO.removeItem(items.get(1));
		questDTO.setEmailOwner("Edwin.schriek@gmail.com");

		QuestDTO newQuest = questService.save(questDTO, true);
		assertThat(newQuest.getItems().size(), is(1));

	}

	@Test
	public void when_delete_item_and_saving_with_same_email_size_must_be_lower() {
		QuestDTO questDTO = questService.getQuestDTO(existingId);

		List<PoiDTO> newItems = new ArrayList<PoiDTO>();
		newItems.add(questDTO.getItems().get(0));

		questDTO.setItems(newItems);

		questService.save(questDTO, true);

		QuestDTO newQuest = questService.getQuestDTO(existingId);

		assertThat(newQuest.getItems().size(), is(1));

	}

	@Test
	public void testSaveSameEmail() {
		QuestDTO quest = new QuestDTO();
		quest.setName("Henk");
		quest.setEmailOwner("Henk@Henk.henk");

		quest = questService.save(quest, true);

		QuestDTO quest2 = questService.save(quest, true);

		assertThat(quest2.getId(), is(quest.getId()));
	}

	@Test
	@Rollback(true)
	public void testSaveExistingNoItems() {
		String name = "test-replaced";
		String email = "kns.arena.tester@gmail.com";
		RoundDTO roundDto = new RoundDTO("round-replaced");
		QuestDTO questDTO = new QuestDTO();
		// questDTO.setId(existingId);
		questDTO.setName(name);
		questDTO.setEmailOwner(email);
		questDTO.setActiveRound(roundDto);
		List<RoundDTO> rounds = new ArrayList<RoundDTO>();
		rounds.add(existingRound);
		questDTO.setRounds(rounds);
		QuestDTO saved = questService.save(questDTO, true);
		Assert.assertNotNull(saved);
		Assert.assertNotNull(saved.getId());
		// Assert.assertEquals(existingId, saved.getId());
		Assert.assertEquals(name, saved.getName());
		Assert.assertEquals(email, saved.getEmailOwner());
		Assert.assertEquals(0, saved.getItems().size());
	}

	@Test
	public void testSaveExistingReplaceItem() {
		String name = "test-replaced";
		String email = "kns.arena.tester@gmail.com";
		RoundDTO roundDto = new RoundDTO("round-replaced");
		QuestDTO questDTO = existingQuest;
		questDTO.setName(name);
		PoiDTO PoiDTO = questDTO.getItems().get(0);
		PoiDTO.setPoint(new SimplePoint(4.4D, 2.2D));
		questDTO.setActiveRound(roundDto);
		List<RoundDTO> rounds = new ArrayList<RoundDTO>();
		rounds.add(existingRound);
		questDTO.setRounds(rounds);
		QuestDTO saved = questService.save(questDTO, true);
		Assert.assertNotNull(saved);
		Assert.assertNotNull(saved.getId());
		Assert.assertEquals(existingId, saved.getId());
		Assert.assertEquals(name, saved.getName());
		Assert.assertEquals(email, saved.getEmailOwner());
		Assert.assertEquals(roundDto, saved.getActiveRound());
		Assert.assertEquals(2, saved.getItems().size());
		Assert.assertEquals(new Double(2.2D), saved.getItems().get(0)
				.getPoint().getLongitude());
		Assert.assertEquals(new Double(4.4D), saved.getItems().get(0)
				.getPoint().getLatitude());
	}

	@Test
	public void testSaveExistingAddItem() {
		QuestDTO questDTO = existingQuest;
		PoiDTO PoiDTO = new PoiDTO("testitem", "Verhaal");
		PoiDTO.setPoint(new SimplePoint(6.6D, 3.3D));

		questDTO.getItems().add(PoiDTO);
		QuestDTO saved = questService.save(questDTO, true);
		Assert.assertEquals(3, saved.getItems().size());
		Assert.assertEquals(new Double(3.3D), saved.getItems().get(2)
				.getPoint().getLongitude());
		Assert.assertEquals(new Double(6.6D), saved.getItems().get(2)
				.getPoint().getLatitude());
	}

	@Test
	public void testSaveExistingRemoveAndAddItem() {
		QuestDTO questDTO = existingQuest;
		questDTO.getItems().remove(0);
		PoiDTO PoiDTO = new PoiDTO("testitem", "Verhaal");

		PoiDTO.setPoint(new SimplePoint(6.6D, 3.3D));
		questDTO.getItems().add(PoiDTO);
		QuestDTO saved = questService.save(questDTO, true);
		Assert.assertEquals(2, saved.getItems().size());
		Assert.assertEquals(new Double(3.3D), saved.getItems().get(1)
				.getPoint().getLongitude());
		Assert.assertEquals(new Double(6.6D), saved.getItems().get(1)
				.getPoint().getLatitude());
	}

	@Test
	public void testSaveNew() {
		String name = "test-new";
		String email = "kns.arena.tester@gmail.com";
		QuestDTO questDTO = new QuestDTO();
		questDTO.setName(name);
		questDTO.setEmailOwner(email);
		QuestDTO saved = questService.save(questDTO, true);
		Assert.assertNotNull(saved);
		Assert.assertNotNull(saved.getId());
		Assert.assertEquals(name, questDTO.getName());
		Assert.assertEquals(email, questDTO.getEmailOwner());
	}

	@Test
	public void testLoad() {
		QuestDTO dto = questService.getQuestDTO(existingId);
		Assert.assertNotNull(dto);
		Assert.assertEquals(existingId, dto.getId());
		Assert.assertEquals(existingName, dto.getName());
		Assert.assertEquals(existingEmail, dto.getEmailOwner());
		Assert.assertEquals(2, dto.getItems().size());
	}

	@Test
	public void testLoadNonExisting() {
		QuestDTO dto = questService.getQuestDTO(-1L);
		Assert.assertNull(dto);
	}

	@Test
	public void testSaveWithAddedQuestion() {
		Participant participant = new Participant("henkz");
		participant = participantRepository.merge(participant);
		QuestDTO questDTO = new QuestDTO();
		questDTO.setName("JAN");
		questDTO.setEmailOwner("NOEMAIL@EMAIL.NL");
		PoiDTO itemDTO = new PoiDTO("eenvraag", "Vraag");
		itemDTO.setPoint(new SimplePoint(2.2D, 1.1D));
		QuestionElement q = new QuestionElement();
		q.setDescription("HAHA");
		q.setOption1("option1");
		q.setOption2("option2");
		q.setOption3("option3");
		q.setOption4("option4");
		q.setCorrectOption(1);
		List<PoiDTO> items = new ArrayList<PoiDTO>();
		items.add(itemDTO);
		questDTO.setItems(items);
		questDTO.setActiveRound(existingRound);
		List<RoundDTO> rounds = new ArrayList<RoundDTO>();
		rounds.add(existingRound);
		questDTO.setRounds(rounds);
		QuestDTO aQuest = questService.save(questDTO, true);

		final Quest quest = questService.getQuest(aQuest.getId());
		final Question question = participantService.getQuestion(quest
				.getPositionables().get(0).getId(), quest);
		final long participantId = participantService
				.getParticipantId(participant.getId().toString());
		final long participationId = questService.participateInQuest(
				participantId, quest);
		participantService.storeParticipationAnswer(participationId, question,
				1);
		System.out.println("AMOUNT POSITIONABLES: "
				+ quest.getPositionables().size());
		System.out.println("KIND OF POSITIONABLE: "
				+ quest.getPositionables().get(0).getName());
		System.out.println("ANSWER OF POSITIONABLE: "
				+ ((Question) quest.getPositionables().get(0).getElements()
						.get(0)).getCorrectAnswer());
		System.out.println("ID QUEST: " + aQuest.getId());

		questDTO.setActiveRound(existingRound);
		aQuest = questService.save(aQuest, false);
		System.out.println("ID QUEST AFTER SAVE: " + aQuest.getId());
	}
}
