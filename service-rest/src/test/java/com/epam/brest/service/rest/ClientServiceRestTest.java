package com.epam.brest.service.rest;

import com.epam.brest.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class ClientServiceRestTest {

    private final Logger log = LogManager.getLogger(ClientServiceRestTest.class);

    public static final String CLIENTS_URL = "http://localhost:8088/clients";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper objectMapper = new ObjectMapper();

    ClientServiceRest clientServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        clientServiceRest = new ClientServiceRest(CLIENTS_URL,restTemplate);
    }
    @Test
    void shouldGetClientById() throws Exception {
        log.debug("shouldGetClientById()");
        //GIVEN
        Integer id = 1;
        Client client = create(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(client))
                );
        // WHEN
        Client testClient = clientServiceRest.getClientById(id);
        //THEN
        mockServer.verify();
        assertNotNull(testClient);
        assertEquals(testClient.getClientId(), id);
        assertEquals(testClient.getClientName(), client.getClientName());
    }

    @Test
    void shouldFindAllClients() throws Exception {
        log.debug("shouldFindAllClients()");
        // GIVEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );
        //WHEN
        List<Client> clients = clientServiceRest.findAll();
        //THEN
        mockServer.verify();
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertTrue(clients.size() == 2);
    }

    @Test
    void shouldCreateClient() throws Exception {
        log.debug("shouldCreateClient()");
        //GIVEN
        int index = 7;
        Client client = new Client();
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("7"))
                );
        //WHEN
        Integer id = clientServiceRest.create(create(index));
        //THEN
        mockServer.verify();
        assertNotNull(id);
        assertEquals(id,index);
    }

    @Test
    void shouldUpdateClient() throws Exception {
        log.debug("shouldUpdateClient()");
        //GIVEN
        Integer id = 1;
        Client client = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(client))
                );
        //WHEN
        int result = clientServiceRest.update(client);
        Client testClient = clientServiceRest.getClientById(id);
        //THEN
        mockServer.verify();
        assertTrue(1 == result);
        assertNotNull(testClient);
        assertEquals(testClient.getClientId(), id);
        assertEquals(testClient.getClientName(), client.getClientName());
    }

    @Test
    void shouldDeleteClient() throws Exception {
        log.debug("shouldDeleteClient()");
        //GIVEN
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        //WHEN
        int result = clientServiceRest.delete(id);
        //THEN
        mockServer.verify();
        assertTrue(1 == result);
    }

    @Test
    void shouldCount() throws Exception {
        log.debug("shouldCount()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CLIENTS_URL + "/count")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        //WHEN
        int result = clientServiceRest.count();

        //THEN
        mockServer.verify();
        assertTrue(result> 0);
    }

    private Client create(int index) {
        Client client = new Client(index,"client" + index);
        return client;
    }
}