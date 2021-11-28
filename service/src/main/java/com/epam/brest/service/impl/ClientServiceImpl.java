package com.epam.brest.service.impl;

import com.epam.brest.dao.ClientDao;
import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService {
    private final Logger log = LogManager.getLogger(ClientServiceImpl.class);

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client getClientById(Integer clientId) {
        log.debug("getClientById(id:{})", clientId);
        return this.clientDao.getClientById(clientId);
    }

    @Override
    @Transactional
    public Integer create(Client client) {
        log.debug("create({})", client);
        return this.clientDao.create(client);
    }

    @Override
    public Integer update(Client client) {
        log.debug("update({})", client);
        return this.clientDao.update(client);
    }

    @Override
    public Integer delete(Integer clientId) {
        log.debug("delete client id:{}",clientId);
        return this.clientDao.delete(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        log.debug("count()");
        return this.clientDao.count();
    }
}
