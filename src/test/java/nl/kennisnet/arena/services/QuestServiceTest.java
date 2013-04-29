package nl.kennisnet.arena.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import nl.kennisnet.arena.client.domain.QuestDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/configuration.xml",
        "classpath:/integration.xml", "classpath:/integration-test.xml","classpath:/services.xml"})
@Transactional
public class QuestServiceTest {
    
    @Autowired
    private QuestService service;
    
    @Test
    public void get_non_existing_quest(){
        QuestDTO questDTO = service.getQuestDTO(0l);
        assertThat(questDTO, is(nullValue()));
    }

}
