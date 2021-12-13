package com.epam.brest.dao;

import com.epam.brest.model.Client;
import com.epam.brest.model.Repair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RepairDaoJDBCImplTest {

    @InjectMocks
    private RepairDaoJDBCImpl repairDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImplTest.class);

    @Captor
    private ArgumentCaptor<RowMapper<Repair>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
    }

    @Test
    void getRepairById() {
    }
}