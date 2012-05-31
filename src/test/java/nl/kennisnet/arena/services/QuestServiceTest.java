package nl.kennisnet.arena.services;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.client.domain.QuestDTO;
import nl.kennisnet.arena.client.domain.QuestItemDTO;
import nl.kennisnet.arena.client.domain.RoundDTO;
import nl.kennisnet.arena.client.domain.SimplePoint;
import nl.kennisnet.arena.model.Round;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {  "classpath:/configuration.xml", "classpath:/integration.xml", "classpath:/services.xml", "classpath:arena-servlet-test.xml" })
@Transactional
public class QuestServiceTest {
    
    @Autowired
    QuestService questService;

    Long existingId;
    QuestDTO existingQuest;
    String existingName = "test-existing";
    String existingEmail = "kns.arena.tester@gmail.com";
    RoundDTO existingRound = new RoundDTO("test-round");
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        QuestDTO questDTO = new QuestDTO();
        questDTO.setName(existingName);
        questDTO.setEmailOwner(existingEmail);
        QuestItemDTO itemDTO = new QuestItemDTO("testitem", "Verhaal");
        itemDTO.setPoint(new SimplePoint(2.2D,1.1D));
        List<QuestItemDTO> items = new ArrayList<QuestItemDTO>();
        items.add(itemDTO);
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
    @Rollback(true)
    public void testSaveExistingNoItems() {
        String name = "test-replaced";
        String email = "kns.arena.tester@gmail.com";
        RoundDTO roundDto = new RoundDTO("round-replaced");
        QuestDTO questDTO = new QuestDTO();
        questDTO.setId(existingId);
        questDTO.setName(name);
        questDTO.setEmailOwner(email);
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
        Assert.assertEquals(0, saved.getItems().size());
    }

    @Test
    public void testSaveExistingReplaceItem() {
        String name = "test-replaced";
        String email = "kns.arena.tester@gmail.com";
        RoundDTO roundDto = new RoundDTO("round-replaced");
        QuestDTO questDTO = existingQuest;
        questDTO.setName(name);
        QuestItemDTO questItemDTO = questDTO.getItems().get(0);
        questItemDTO.setPoint(new SimplePoint(4.4D,2.2D));
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
        Assert.assertEquals(1, saved.getItems().size());
        Assert.assertEquals(new Double(2.2D), saved.getItems().get(0).getPoint().getLongitude());
        Assert.assertEquals(new Double(4.4D), saved.getItems().get(0).getPoint().getLatitude());
    }

    @Test
    public void testSaveExistingAddItem() {
        QuestDTO questDTO = existingQuest;
        QuestItemDTO questItemDTO = new QuestItemDTO("testitem", "Verhaal");
        questItemDTO.setPoint(new SimplePoint(6.6D,3.3D));

        questDTO.getItems().add(questItemDTO);
        QuestDTO saved = questService.save(questDTO, true);
        Assert.assertEquals(2, saved.getItems().size());
        Assert.assertEquals(new Double(3.3D), saved.getItems().get(1).getPoint().getLongitude());
        Assert.assertEquals(new Double(6.6D), saved.getItems().get(1).getPoint().getLatitude());
    }
    
    @Test
    public void testSaveExistingRemoveAndAddItem() {
        QuestDTO questDTO = existingQuest;
        questDTO.getItems().remove(0);
        QuestItemDTO questItemDTO = new QuestItemDTO("testitem", "Verhaal");

        questItemDTO.setPoint(new SimplePoint(6.6D,3.3D));
        questDTO.getItems().add(questItemDTO);
        QuestDTO saved = questService.save(questDTO, true);
        Assert.assertEquals(1, saved.getItems().size());
        Assert.assertEquals(new Double(3.3D), saved.getItems().get(0).getPoint().getLongitude());
        Assert.assertEquals(new Double(6.6D), saved.getItems().get(0).getPoint().getLatitude());
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
        Assert.assertEquals(1, dto.getItems().size());
    }

    @Test
    public void testLoadNonExisting() {
        QuestDTO dto = questService.getQuestDTO(-1L);
        Assert.assertNull(dto);
    }

}
