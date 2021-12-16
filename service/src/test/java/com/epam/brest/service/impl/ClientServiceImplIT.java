package com.epam.brest.service.impl;

import com.epam.brest.dao.exception.DuplicateEntityException;
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

import java.util.List;

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
        assertNotNull(clientService);
    }
    @Test
    void test_findAll(){
        log.debug("test_findAll()");
        assertNotNull(clientService.findAll());
        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertFalse(clients.isEmpty());

    }


    @Test
    void create() {
        log.debug("Execute test: create(");
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
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        assertThrows(DuplicateEntityException.class, () -> {
            clientService.create(client);
            clientService.create(client);
        });
    }
    @Test
    void test_getClientById(){
        log.debug("test_getClientById()");
        Client client = new Client("Vorisuk Oleg Aleksandrovich");
        Integer clientId = clientService.create(client);
        Client testedClient = clientService.getClientById(clientId);

        assertEquals(client.getClientName().toUpperCase(), testedClient.getClientName());

    }
    @Test
    void test_update(){
        log.debug("test_update()");
        List<Client> clients = clientService.findAll();
        if (clients.size() == 0) {
            clientService.create(new Client("Tested Client"));
            clients = clientService.findAll();
        }
        Client clientSrc = clients.get(0);
        Client clientDst = clientService.getClientById(clientSrc.getClientId());
        assertEquals(clientSrc.getClientName(), clientDst.getClientName());
    }
    @Test
    void test_delete(){
        log.debug("test_delete()");
       Integer clientId = 3;
       Integer sizeBefore = clientService.count();
       assertNotNull(sizeBefore);
       clientService.delete(clientId);
       Integer sizeAfter = clientService.count();
       assertEquals(sizeBefore,sizeAfter + 1);
    }

    @Test
    void shouldCount(){
        log.debug("shouldCount()");

        Integer amount = clientService.count();
        assertNotNull(amount);
        assertTrue(amount > 0);
        assertEquals(Integer.valueOf(3),amount);
    }
}