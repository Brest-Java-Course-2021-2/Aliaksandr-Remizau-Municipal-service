package com.epam.brest.rest;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class ClientDtoControllerTest {
    private static final Logger log = LogManager.getLogger(ClientDtoControllerTest.class);

    @InjectMocks
     private ClientDtoController clientDtoController;

    @Mock
    private ClientDtoService clientDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(clientDtoController)
                .build();
    }
    @AfterEach
    public void end(){
        Mockito.verifyNoMoreInteractions(clientDtoService);
    }

    @Test
    public void shouldFindAllClientDtoWithCountRepairs() throws Exception {
        log.debug("shouldFindAllClientDtoWithCountRepairs()");
        Mockito.when(clientDtoService.findAllWithRepairs()).thenReturn(Arrays.asList(create(0),create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/clients_dto")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientName", Matchers.is("x0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfRepairs", Matchers.is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientName", Matchers.is("x1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfRepairs", Matchers.is(12)))
        ;

        Mockito.verify(clientDtoService).findAllWithRepairs();
    }

    private ClientDto create(int index){
        log.debug("create () clientDto with id:{}",index);
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(index);
        clientDto.setClientName("x" + index);
        clientDto.setNumberOfRepairs(11 + index);
        return clientDto;
    }

}
