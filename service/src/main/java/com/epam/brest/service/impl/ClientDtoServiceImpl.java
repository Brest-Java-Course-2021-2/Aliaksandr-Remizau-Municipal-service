package com.epam.brest.service.impl;

import com.epam.brest.dao.ClientDtoDao;
import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *  Client DTO Service implementation.
 */
@Service
@Transactional
public class ClientDtoServiceImpl implements ClientDtoService {
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(ClientDtoServiceImpl.class);
    /**
     * injection dependency ClientDtoDao.
     */
    private final ClientDtoDao clientDtoDao;
    /**
     * Constructor with  NamedParameterJdbcTemplate.
     *
     * @param clientDtoDao
     */
    public ClientDtoServiceImpl(ClientDtoDao clientDtoDao) {
        this.clientDtoDao = clientDtoDao;
    }

    /**
     * Get all clients with number of their repairs.
     *
     * @return clients list.
     */
    @Override
    public List<ClientDto> findAllWithRepairs() {
        log.debug("findAllWithRepairs()");
        return clientDtoDao.findAllWithRepairs();
    }
}
