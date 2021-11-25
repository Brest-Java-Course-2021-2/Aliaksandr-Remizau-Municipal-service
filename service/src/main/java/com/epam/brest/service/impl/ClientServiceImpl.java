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
    @Transactional
    public Integer create(Client client) {
        log.debug("create({})",client);
        return this.clientDao.create(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        log.debug("count()");
        return this.clientDao.count();
    }
}
