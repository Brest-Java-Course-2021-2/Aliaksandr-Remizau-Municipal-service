package com.epam.brest.dao;

import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.model.Client;
import com.epam.brest.db.SpringJdbcConfig;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({ClientDaoJDBCImpl.class})
@PropertySource({"classpath:dao-sql.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class ClientDaoJDBCImplIT {
    private final Logger log = LogManager.getLogger(ClientDaoJDBCImplIT.class);

    @Autowired
    private  ClientDaoJDBCImpl clientDaoJDBC;

    @Test
    void testFindAll() {
        log.debug("Execute test : findAll()");
        assertNotNull(clientDaoJDBC);
        assertNotNull(clientDaoJDBC.findAll());
    }
    @Test
    void testCreate(){
        log.debug("Execute test: create(");
        assertNotNull(clientDaoJDBC);
        int clientSizeBefore = clientDaoJDBC.count();
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        int newClientId = clientDaoJDBC.create(client);
        assertNotNull(newClientId);
        assertEquals((int) clientSizeBefore, clientDaoJDBC.count() - 1);
    }

    @Test
    void testTryToCreateEqualsClient() {
        log.debug("Execute test: tryToCreateEqualsClient()");
        assertNotNull(clientDaoJDBC);
        Client client = new Client("Borisuk Oleg Aleksandrovich");
        assertThrows(DuplicateEntityException.class, () -> {
            clientDaoJDBC.create(client);
            clientDaoJDBC.create(client);
        });
    }

    @Test
    void testGetClientById() {
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
    void testUpdateClient() {
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
    void testDeleteClient() {
        clientDaoJDBC.create(new Client("Tested Client"));
        List<Client> clients = clientDaoJDBC.findAll();

        clientDaoJDBC.delete(clients.get(clients.size() - 1).getClientId());
        assertEquals(clients.size() - 1, clientDaoJDBC.findAll().size());
    }

    @Test
    void testShouldCount() {
        assertNotNull(clientDaoJDBC);
        Integer quantity = clientDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}