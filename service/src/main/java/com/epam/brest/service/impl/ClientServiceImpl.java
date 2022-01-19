package com.epam.brest.service.impl;

import com.epam.brest.dao.ClientDao;
import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.impl.exceptions.ClientNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Client Service implementation.
 */
@Service
public class ClientServiceImpl implements ClientService {
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(ClientServiceImpl.class);
    /**
     * injection dependency ClientDao.
     */
    private final ClientDao clientDao;

    /**
     * Constructor with  NamedParameterJdbcTemplate.
     *
     * @param clientDao
     */
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {

        log.debug("findAll()");

        return this.clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientById(Integer clientId) {

        log.debug("getClientById(id:{})", clientId);

        try {
            return this.clientDao.getClientById(clientId);
        } catch (EmptyResultDataAccessException e) {
            throw new ClientNotFoundException(clientId);
        }
    }

    @Override
    @Transactional
    public Integer create(Client client) {

        log.debug("create({})", client);

        return this.clientDao.create(client);
    }

    @Override
    @Transactional
    public Integer update(Client client) {

        log.debug("update({})", client);
        try {
            clientDao.getClientById(client.getClientId());
            return this.clientDao.update(client);
        }catch (EmptyResultDataAccessException ex){
            throw new ClientNotFoundException(client.getClientId());
        }
    }

    @Override
    @Transactional
    public Integer delete(Integer clientId) {

        log.debug("delete client id:{}",clientId);
        try {
            return this.clientDao.delete(clientId);
        }catch (EmptyResultDataAccessException ex){
            throw new ClientNotFoundException(clientId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {

        log.debug("count()");

        return this.clientDao.count();
    }
}
