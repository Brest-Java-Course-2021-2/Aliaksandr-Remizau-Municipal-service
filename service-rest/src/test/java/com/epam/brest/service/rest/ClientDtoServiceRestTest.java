package com.epam.brest.service.rest;

import com.epam.brest.model.dto.ClientDto;
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
class ClientDtoServiceRestTest {
    private final Logger log = LogManager.getLogger(ClientDtoServiceRestTest.class);

    public static final String URL = "http://localhost:8088/clients_dto";
    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    ClientDtoServiceRest clientDtoService;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        clientDtoService = new ClientDtoServiceRest(URL,restTemplate);
    }

    @Test
    void findAllWithRepairs() throws Exception {
        log.debug("findAllWithRepairs()");
        //GIVEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(1), create(2))))
                );
        //WHEN
        List<ClientDto> clientsDto = clientDtoService.findAllWithRepairs();
        //THEN
        mockServer.verify();
        assertNotNull(clientsDto);
        assertFalse(clientsDto.isEmpty());
        assertTrue(clientsDto.size() == 2);
    }

    private ClientDto create(int index) {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(index);
        clientDto.setClientName("client" + index);
        clientDto.setNumberOfRepairs(index + 2);
        return clientDto;
    }
}