package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
public class ClientDaoJDBCImplTest {

    @InjectMocks
    private ClientDaoJDBCImpl clientDaoJDBC;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LogManager.getLogger(ClientDaoJDBCImplTest.class);

    @Captor
    private ArgumentCaptor<RowMapper<Client>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }


    @Test
    public void findAll(){
        log.debug("findAll()");
        String sql = "select";
        ReflectionTestUtils.setField(clientDaoJDBC,"sqlAllClients",sql);
        Client client = new Client();
        List<Client> singletonList = Collections.singletonList(client);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Client>>any()))
                .thenReturn(singletonList);
        List<Client> resultList = clientDaoJDBC.findAll();
        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());

        RowMapper<Client> clientRowMapper = captorMapper.getValue();
        assertNotNull(clientRowMapper);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertSame(client,resultList.get(0));
    }

    @Test
    public void getClientById(){
        log.debug("get clientById()");
        int id = 0;
        String sql = "select";
        ReflectionTestUtils.setField(clientDaoJDBC,"sqlClientById",sql);
        Client client = new Client();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                        any(),
                        ArgumentMatchers.<SqlParameterSource>any(),
                        ArgumentMatchers.<RowMapper<Client>>any()))
                        .thenReturn(client);
        Client result = clientDaoJDBC.getClientById(id);
        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Client> mapper = captorMapper.getValue();

      assertNotNull(source);
      assertNotNull(mapper);

       assertNotNull(result);
       assertSame(client, result);
    }
}
