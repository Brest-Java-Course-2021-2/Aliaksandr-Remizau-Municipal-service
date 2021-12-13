package com.epam.brest.dao;

import com.epam.brest.model.dto.ClientDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

/**
 *  Client DTO DAO implementation.
 */

public class ClientDtoDaoJDBCImpl implements ClientDtoDao{
    private final Logger log = LogManager.getLogger(ClientDtoDaoJDBCImpl.class);
    /**
     * NamedParameterJdbcTemplate.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * SQL query for find all clients with repairs.
     */
    @Value("${findAllWithRepairsSql}")
    private String findAllWithRepairsSql;


    public ClientDtoDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ClientDto> findAllWithRepairs() {
        log.debug("Start: findAllWithRepairs()");
        List<ClientDto> clients = namedParameterJdbcTemplate.query(findAllWithRepairsSql,
                BeanPropertyRowMapper.newInstance(ClientDto.class));
        return clients;
    }
}
