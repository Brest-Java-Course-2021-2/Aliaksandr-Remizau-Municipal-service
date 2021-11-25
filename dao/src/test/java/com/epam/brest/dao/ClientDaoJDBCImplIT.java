package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
@Rollback
class ClientDaoJDBCImplIT {
    private final Logger log = LogManager.getLogger(ClientDaoJDBCImplIT.class);

    ClientDaoJDBCImpl clientDaoJDBC;

    public ClientDaoJDBCImplIT(@Autowired ClientDao clientDaoJDBC) {
        this.clientDaoJDBC = (ClientDaoJDBCImpl) clientDaoJDBC;
    }

    @Test
    void findAll() {
        log.debug("Execute test : findAll()");
        assertNotNull(clientDaoJDBC);
        assertNotNull(clientDaoJDBC.findAll());
    }
    @Test
    void create(){
        log.debug("Execute test: create(");
        assertNotNull(clientDaoJDBC);
        int clientSizeBefore = clientDaoJDBC.count();
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        int newClientId = clientDaoJDBC.create(client);
        assertNotNull(newClientId);
        assertEquals((int) clientSizeBefore, clientDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateEqualsClient() {
        log.debug("Execute test: tryToCreateEqualsClient()");
        assertNotNull(clientDaoJDBC);
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        assertThrows(IllegalArgumentException.class, () -> {
            clientDaoJDBC.create(client);
            clientDaoJDBC.create(client);
        });
    }

    @Test
    void shouldCount() {
        assertNotNull(clientDaoJDBC);
        Integer quantity = clientDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}