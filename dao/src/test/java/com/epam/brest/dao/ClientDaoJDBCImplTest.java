package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
class ClientDaoJDBCImplTest {

    ClientDaoJDBCImpl clientDaoJDBC;

    public ClientDaoJDBCImplTest(@Autowired ClientDao clientDaoJDBC) {
        this.clientDaoJDBC = (ClientDaoJDBCImpl) clientDaoJDBC;
    }

    @Test
    void findAll() {
        assertNotNull(clientDaoJDBC);
        assertNotNull(clientDaoJDBC.findAll());
    }
    @Test
    void create(){
        assertNotNull(clientDaoJDBC);
        int clientSizeBefore = clientDaoJDBC.findAll().size();
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        int newDepartmentId = clientDaoJDBC.create(client);
        assertNotNull(newDepartmentId);
        assertEquals((int) clientSizeBefore, clientDaoJDBC.findAll().size() - 1);

    }
    @Test
    void tryToCreateEqualsClient() {
        assertNotNull(clientDaoJDBC);
        Client client = new Client("Olenev Grigoriy Ivanovich");
        assertThrows(DuplicateKeyException.class, () -> {
            clientDaoJDBC.create(client);
            clientDaoJDBC.create(client);
        });
    }
}