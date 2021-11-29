package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ClientDaoJDBCImplTest {
    @InjectMocks
    private ClientDaoJDBCImpl clientDtoDaoJDBC;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private final Logger log = LogManager.getLogger(ClientDaoJDBCImplTest.class);

    @Test
    public void findAll(){
        log.debug("findAll()");
        Client client = new Client();
        List<Client> singletonList = Collections.singletonList(client);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Client>>any()))
                .thenReturn(singletonList);
        List<Client> resultList = clientDtoDaoJDBC.findAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertSame(client,resultList.get(0));
    }
}
