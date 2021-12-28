package com.epam.brest.web_app;

import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.epam.brest.service.RepairService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class RepairControllerIT {
    private final Logger log = LogManager.getLogger(RepairControllerIT.class);
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RepairService repairService;

    private MockMvc mockMvc;
    private static Repair testedRepair;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        testedRepair = new Repair(RepairType.ELECTRIC,"some Test Address", LevelOfDifficulty.HARD, LocalDate.of(2021,12,31),1);

    }
    @Test
    public void shouldReturnRepairs() throws Exception {
        log.debug("shouldReturnRepairs()");
        assertNotNull(repairService);
        mockMvc.perform(get("/repairs")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("repairs"));
    }
    @Test
    public void goToAddRepairPage() throws Exception {
        log.debug("goToAddRepairPage()");
        mockMvc.perform(get("/repair")
                        .flashAttr("repair", testedRepair))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("repair"));

    }

    @Test
    public void shouldReturnEditPageById(){
        log.debug("shouldReturnEditPageById()");

    }

    void shouldFilterRepairsWithCorrectDate() throws Exception {
        log.debug("shouldFilterRepairsWithCorrectDate()");
        mockMvc.perform(get("/repairs/filter")
                        .param("startLimitDate", String.valueOf(LocalDate.of(2021, 12, 20)))
                        .param("endLimitDate", String.valueOf(LocalDate.of(2021, 12, 31))))
                .andExpect(status().isOk())
                .andExpect(view().name("repairs"));
    }
}
