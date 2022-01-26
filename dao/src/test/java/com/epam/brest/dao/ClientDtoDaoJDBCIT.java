package com.epam.brest.dao;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({ClientDtoDaoJDBCImpl.class})
@PropertySource({"classpath:dao-sql.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class ClientDtoDaoJDBCIT {

    private final Logger log = LogManager.getLogger(ClientDtoDaoJDBCIT.class);

    @Autowired
    private  ClientDtoDaoJDBCImpl clientDtoDaoJDBC;

    @Test
    void test_findAllWithRepairs() {

        log.debug("test_findAllWithRepairs()");
        assertNotNull(clientDtoDaoJDBC);
        assertNotNull(clientDtoDaoJDBC.findAllWithRepairs());
        List<ClientDto> clients = clientDtoDaoJDBC.findAllWithRepairs();
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
    }
}