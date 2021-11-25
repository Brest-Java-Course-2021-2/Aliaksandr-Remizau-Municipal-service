package com.epam.brest.service.impl;

import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class ClientServiceImplIT {
    private final Logger log = LogManager.getLogger(ClientServiceImplIT.class);

    @Autowired
    ClientService clientService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        log.debug("Execute test: create(");
        assertNotNull(clientService);
        int clientSizeBefore = clientService.count();
        assertNotNull(clientSizeBefore);
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        int newClientId = clientService.create(client);
        assertNotNull(newClientId);
        assertEquals((int) clientSizeBefore, clientService.count() - 1);
    }

    @Test
    void tryToCreateEqualsClient() {
        log.debug("Execute test: tryToCreateEqualsClient()");
        assertNotNull(clientService);
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        assertThrows(IllegalArgumentException.class, () -> {
            clientService.create(client);
            clientService.create(client);
        });
    }

    @Test
    void shouldCount(){
        log.debug("shouldCount()");
        assertNotNull(clientService);
        Integer amount = clientService.count();
        assertNotNull(amount);
        assertTrue(amount > 0);
        assertEquals(Integer.valueOf(3),amount);
    }
}