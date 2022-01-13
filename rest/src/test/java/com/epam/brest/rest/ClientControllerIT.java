package com.epam.brest.rest;


import com.epam.brest.model.Client;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.model.constants.ClientConstants.CLIENT_NAME_SIZE;
import static com.epam.brest.rest.exception.CustomExceptionHandler.CLIENT_NOT_FOUND;
import static com.epam.brest.rest.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class ClientControllerIT {

    private static final Logger log = LogManager.getLogger(ClientControllerIT.class);

    public static final String CLIENTS_ENDPOINT = "/clients";
    @Autowired
    private ClientController clientController;
    @Autowired
    private CustomExceptionHandler customExceptionHandler;;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcClientService mockClientService = new MockMvcClientService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldCreateClient() throws Exception {

        log.debug("shouldCreateClient()");
        Client client = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        Integer id = mockClientService.create(client);
        assertNotNull(id);
    }

//    @Test
//    public void shouldFailCreateClientWithDuplicateName() throws Exception {
//
//        log.debug("shouldFailCreateClientWithDuplicateName()");
//
//        Client clientFirst = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
//        Integer id = mockClientService.create(clientFirst);
//        assertNotNull(id);
//        Client clientSecond =new Client(clientFirst.getClientName());
//
//        MockHttpServletResponse response =
//                mockMvc.perform(post(CLIENTS_ENDPOINT)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(clientSecond))
//                                .accept(MediaType.APPLICATION_JSON)
//                        ).andExpect(status().isUnprocessableEntity())
//                        .andReturn().getResponse();
//
//        assertNotNull(response);
//        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
//        assertNotNull(errorResponse);
//        assertTrue(response.getContentAsString()
//                .contains(String.format("Client with the same name already exists in DB", clientFirst.getClientName())));
//    }

    @Test
    public void shouldFindAll() throws Exception {

        log.debug("shouldFindAll()");
        // GIVEN
        Client client = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        Integer id = mockClientService.create(client);
        assertNotNull(id);
        // WHEN
        List<Client> clients = mockClientService.findAll();
        // THEN
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
    }

    @Test
    void shouldFindClientById() throws Exception {

        log.debug("shouldFindClientById()");
        // GIVEN
        Client client = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        Integer id = mockClientService.create(client);
        assertNotNull(id);
        // WHEN
        Optional<Client> optionalClient = mockClientService.findById(id);
        // THEN
        assertTrue(optionalClient.isPresent());
        assertEquals(optionalClient.get().getClientId(), id);
        assertEquals(client.getClientName().toUpperCase(), optionalClient.get().getClientName());
    }
    @Test
    public void shouldReturnClientNotFoundError() throws Exception {

        log.debug("shouldReturnClientNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(CLIENTS_ENDPOINT + "/109")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), CLIENT_NOT_FOUND);
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        log.debug("shouldUpdateClient()");

        // GIVEN
        Client client = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        Integer id = mockClientService.create(client);
        assertNotNull(id);

        Optional<Client> optionalClient = mockClientService.findById(id);
        assertTrue(optionalClient.isPresent());

        optionalClient.get().
                setClientName(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        // WHEN
        int result = mockClientService.update(optionalClient.get());
        // THEN
        assertTrue(1 == result);

        Optional<Client> updatedOptionalClient = mockClientService.findById(id);
        assertTrue(updatedOptionalClient .isPresent());
        assertEquals(updatedOptionalClient .get().getClientId(), id);
        assertEquals(updatedOptionalClient.get().getClientName(),optionalClient.get().getClientName());
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        log.debug("shouldDeleteClient()");
        // GIVEN
        Client client = new Client(RandomStringUtils.randomAlphabetic(CLIENT_NAME_SIZE));
        Integer id = mockClientService.create(client);
        assertNotNull(id);

        List<Client> clients = mockClientService.findAll();
        assertNotNull(clients);
        // WHEN
        int result = mockClientService.delete(id);
        // THEN
        assertTrue(1 == result);

        List<Client> afterDeleteClients = mockClientService.findAll();
        assertNotNull(afterDeleteClients);
        assertTrue(clients.size()-1 == afterDeleteClients.size());
    }



    class MockMvcClientService {

        public List<Client> findAll() throws Exception {
            log.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(ClientControllerIT.CLIENTS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Client>>() {});
        }

        public Optional<Client> findById(Integer id) throws Exception {

            log.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(CLIENTS_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Client.class));
        }

        public Integer create(Client client) throws Exception {

            log.debug("create({})", client);
            String json = objectMapper.writeValueAsString(client);

            MockHttpServletResponse response =
                    mockMvc.perform(post(CLIENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Client client) throws Exception {

            log.debug("update({})", client);
            MockHttpServletResponse response =
                    mockMvc.perform(put(CLIENTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(client))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer clientId) throws Exception {

            log.debug("delete(id:{})", clientId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(CLIENTS_ENDPOINT).append("/")
                                            .append(clientId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
