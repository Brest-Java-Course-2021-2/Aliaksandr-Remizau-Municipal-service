package com.epam.brest.rest;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ClientDtoControllerTest {

    @InjectMocks
    ClientDtoController clientDtoController;

    @Mock
    ClientDtoService clientDtoService;

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
    public void shouldFindAllClientDtoWithCountRepairs(){

    }
    private ClientDto create(){
        return null;
    }

}
