package com.epam.brest.web_app;

import com.epam.brest.dao.ClientDao;
import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class ClientControllerIT {
    private final Logger log = LogManager.getLogger(ClientControllerIT.class);
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ClientService clientService;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @org.junit.jupiter.api.Test
    void shouldReturnClientsPage() throws Exception {
        log.debug("shouldReturnClientsPage()");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/clients")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("clients"))
                //TODO:method
                .andExpect(model().attribute("clients", hasItem(
                        allOf(
                                hasProperty("clientId", is(1)),
                                hasProperty("clientName", is("Aleksandrovich Aleksey Iosifovich")),
                                hasProperty("numberOfRepairs", is(2))
                        )
                )))
                .andExpect(model().attribute("clients", hasItem(
                        allOf(
                                hasProperty("clientId", is(2)),
                                hasProperty("clientName", is("Orlov Petr Ivanovich")),
                                hasProperty("numberOfRepairs", is(2))
                        )
                )))
                .andExpect(model().attribute("clients", hasItem(
                        allOf(
                                hasProperty("clientId", is(3)),
                                hasProperty("clientName", is("Borodach Michail Ivanovich")),
                                hasProperty("numberOfRepairs", is(1))
                        )
                )));
    }
    @Test
    void shouldAddClient() throws Exception{
        log.debug("Execute test: shouldAddClient()");
        //WHEN
        assertNotNull(clientService);
        int clientSizeBefore = clientService.count();
        assertNotNull(clientSizeBefore);
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        //THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("clientName", client.getClientName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"))
                .andExpect(redirectedUrl("/clients"));
        //VERIFY
        assertEquals((int) clientSizeBefore, clientService.count() - 1);
    }

}