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

import java.util.List;

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
    void getClientById() {
        log.debug("getClientById()");
        List<Client> clients = clientDaoJDBC.findAll();
        if (clients.size() == 0) {
            clientDaoJDBC.create(new Client("Tested Client"));
            clients = clientDaoJDBC.findAll();
        }
        Client clientSrc = clients.get(0);
        Client clientDSt = clientDaoJDBC.getClientById(clientSrc.getClientId());
        assertEquals(clientSrc.getClientName(), clientDSt.getClientName());

    }

    @Test
    void updateClient() {
        log.debug("updateClient()");
        List<Client> clients = clientDaoJDBC.findAll();
        if (clients.size() == 0) {
            clientDaoJDBC.create(new Client("Tested Client"));
            clients = clientDaoJDBC.findAll();
        }
        Client clientSrc = clients.get(0);
        Client clientDst = clientDaoJDBC.getClientById(clientSrc.getClientId());
        assertEquals(clientSrc.getClientName(), clientDst.getClientName());

    }

    @Test
    void deleteClient() {
        clientDaoJDBC.create(new Client("Tested Client"));
        List<Client> clients = clientDaoJDBC.findAll();

        clientDaoJDBC.delete(clients.get(clients.size() - 1).getClientId());
        assertEquals(clients.size() - 1, clientDaoJDBC.findAll().size());
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