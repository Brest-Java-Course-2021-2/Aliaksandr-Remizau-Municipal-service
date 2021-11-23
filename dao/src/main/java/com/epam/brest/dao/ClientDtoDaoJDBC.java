package com.epam.brest.dao;

import com.epam.brest.model.dto.ClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

/**
 *  Client DTO DAO implementation.
 */

public class ClientDtoDaoJDBC implements ClientDtoDao{
    /**
     * NamedParameterJdbcTemplate.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * SQL query for find all clients with repairs.
     */

    private String findAllWithRepairsSql = "SELECT\n" +
            "\tc.client_id AS clientId,\n" +
            "\tc.client_name AS clientName,\n" +
            "\tcount(r.client_Id) AS numberOfRepairs\n" +
            "FROM\n" +
            "\tclient c\n" +
            "LEFT JOIN repair r ON\n" +
            "\tc.client_id = r.client_id\n" +
            "GROUP BY\n" +
            "\tc.client_id,\n" +
            "\tc.client_name\n" +
            "ORDER BY\n" +
            "\tclient_name";
//     @Value("${findAllWithRepairsSql}")
//    private String findAllWithRepairsSql;


    public ClientDtoDaoJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ClientDto> findAllWithRepairs() {
        List<ClientDto> clients = namedParameterJdbcTemplate.query(findAllWithRepairsSql,
                BeanPropertyRowMapper.newInstance(ClientDto.class));
        return clients;
    }
}
