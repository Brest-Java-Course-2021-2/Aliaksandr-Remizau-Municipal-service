package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
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
        int clientSizeBefore = clientDaoJDBC.findAll().size();
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        int newDepartmentId = clientDaoJDBC.create(client);
        assertNotNull(newDepartmentId);
        assertEquals((int) clientSizeBefore, clientDaoJDBC.findAll().size() - 1);

    }
    @Test
    void tryToCreateEqualsClient() {
        log.debug("Execute test: tryToCreateEqualsClient()");
        assertNotNull(clientDaoJDBC);
        Client client = new Client("Olenev Grigoriy Ivanovich");
        assertThrows(DuplicateKeyException.class, () -> {
            clientDaoJDBC.create(client);
            clientDaoJDBC.create(client);
        });
    }
}