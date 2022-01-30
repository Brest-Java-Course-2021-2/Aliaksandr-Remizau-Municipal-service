package com.epam.brest.web_app;

import com.epam.brest.model.Client;
import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
class ClientControllerIT {
    private static final String CLIENT_DTO_URL = "http://localhost:8088/clients_dto";
    private static final String CLIENT_URL = "http://localhost:8088/clients";
    private final Logger log = LogManager.getLogger(ClientControllerIT.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
     private ClientDtoService clientDtoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    //@Disabled(("Do not run before fix"))
    void shouldReturnClientsPage() throws Exception {
        log.debug("shouldReturnClientsPage()");


        ClientDto clientDto1 = new ClientDto(1,"Client1",2);
        ClientDto clientDto2 = new ClientDto(2,"Client2",4);

        List<ClientDto> clientDtoList = Arrays.asList(clientDto1, clientDto2);
        mockServer.expect(once(), requestTo(new URI(CLIENT_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(clientDtoList))
                );
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/clients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("clients"))
                .andExpect(model().attribute("clients", hasItem(
                        allOf(
                                hasProperty("clientId", is(1)),
                                hasProperty("clientName", is("Client1")),
                                hasProperty("numberOfRepairs", is(2))
                        )
                )))
                .andExpect(model().attribute("clients", hasItem(
                        allOf(
                                hasProperty("clientId", is(2)),
                                hasProperty("clientName", is("Client2")),
                                hasProperty("numberOfRepairs", is(4))
                        )
                )));
        // VERIFY
        mockServer.verify();

    }

    @Test
    void shouldAddClient() throws Exception {
        log.debug("shouldAddClient()");
        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENT_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        Client client = new Client("Client1");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("clientName", client.getClientName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"))
                .andExpect(redirectedUrl("/clients"));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldOpenEditClientPageById() throws Exception {
        log.debug("shouldOpenEditClientPageById()");

        Client client = new Client(1,"Petrov Andrey");
        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENT_URL + "/" + client.getClientId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(client))
                );
        // THEN
        mockMvc.perform(
                        get("/client/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("client"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("client", hasProperty("clientId", is(1))))
                .andExpect(model()
                        .attribute("client", hasProperty("clientName", is("Petrov Andrey"))));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldUpdateClientAfterEdit() throws Exception {
        log.debug("shouldUpdateClientAfterEdit()");

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENT_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testName = "Petrov Andrey";

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/client/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("clientId", "1")
                                .param("clientName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/clients"))
                .andExpect(redirectedUrl("/clients"));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        log.debug("shouldDeleteClient()");
        int id = 1;

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        // THEN
        mockMvc.perform(
                        get("/client/1/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/clients"))
                .andExpect(redirectedUrl("/clients"));

        // VERIFY
        mockServer.verify();
    }

    @Test
    void shouldFailAddClientOnEmptyName() throws Exception {
        log.debug("shouldFailAddClientOnEmptyName()");
        // WHEN
        Client client = new Client("");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("clientName", client.getClientName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("client"))
                .andExpect(
                        model().attributeHasFieldErrors(
                                "client", "clientName"
                        )
                );
    }
}