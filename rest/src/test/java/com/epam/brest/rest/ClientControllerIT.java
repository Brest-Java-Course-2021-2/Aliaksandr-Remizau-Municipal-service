package com.epam.brest.rest;

import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class ClientControllerIT {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Autowired
     private ObjectMapper objectMapper ;

    private MockMvc mockMvc;



    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(new CustomExceptionHandler())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }
    @Test
    public void findAll(){}



    @Test
    void getClientById() {
    }
    @Test
    void getClientByIdException() throws Exception {
        Mockito.when(clientService.getClientById(anyInt())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(
                MockMvcRequestBuilders.get("clients/876")).
                andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}
