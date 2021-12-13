package com.epam.brest.dao;

import com.epam.brest.model.dto.ClientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
@Rollback
class ClientDtoDaoJDBCIT {

    private final Logger log = LogManager.getLogger(ClientDtoDaoJDBCIT.class);

    private final ClientDtoDaoJDBCImpl clientDtoDaoJDBC;

    public ClientDtoDaoJDBCIT(@Autowired ClientDtoDao clientDtoDaoJDBC) {
        this.clientDtoDaoJDBC = (ClientDtoDaoJDBCImpl) clientDtoDaoJDBC;
    }

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