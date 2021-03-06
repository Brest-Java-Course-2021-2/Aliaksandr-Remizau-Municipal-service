package com.epam.brest.dao;

import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.dao.exception.EmptyFieldEntityException;
import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *  Client DAO implementation.
 */
@Component
public class ClientDaoJDBCImpl implements ClientDao {
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(ClientDaoJDBCImpl.class);
    /**
     * NamedParameterJdbcTemplate.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * SQL query for select all clients.
     */
    @Value("${SQL_ALL_CLIENTS}")
    private  String sqlAllClients;
    /**
     * SQL query for select client by ID.
     */
    @Value("${SQL_CLIENT_BY_ID}")
    private  String sqlClientById;

    /**
     * SQL query for create.
     */
    @Value("${SQL_CREATE_CLIENT}")
    private  String sqlCreateClient;

    /**
     * SQL query for count client with this name.
     */
    @Value("${SQL_CHECK_UNIQUE_CLIENT_NAME}")
    private  String sqlCheckUniqueClientName;

    /**
     * SQL query for update client name.
     */
    @Value("${SQL_UPDATE_CLIENT_NAME}")
    private  String sqlUpdateClientName;

    /**
     * SQL query for delete client by ID.
     */
    @Value("${SQL_DELETE_CLIENT_BY_ID}")
    private  String sqlDeleteClientById;

    /**
     * SQL query for count clients.
     */
    @Value("${SQL_COUNT_CLIENT}")
    private  String sqlCountClient;

    /**
     * Constructor with  NamedParameterJdbcTemplate.
     *
     * @param namedParameterJdbcTemplate
     */
    public ClientDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Client> findAll() {

        log.debug("Execute method: findAll");

        return namedParameterJdbcTemplate.query(sqlAllClients, new ClientRowMapper());
    }

    @Override
    public Client getClientById(Integer clientId) {

        log.debug("getClientById id:{}", clientId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientId", clientId);

        return namedParameterJdbcTemplate.queryForObject(sqlClientById, sqlParameterSource, new ClientRowMapper() {
        });
    }

    @Override
    public Integer create(Client client) {
        log.debug("Create client : {}", client);

        if (!isClientUnique(client.getClientName())) {
            log.warn("Client with the same name {} already exists.", client.getClientName());
            throw new DuplicateEntityException("Client with the same name already exists in DB.");
        }
        if(client.getClientName().isEmpty()){
            log.warn("Please provide client name!");
            throw new EmptyFieldEntityException("Please provide client name!");
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", client.getClientName().toUpperCase());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateClient, sqlParameterSource, keyHolder);

        return (Integer) keyHolder.getKey();
    }

    private boolean isClientUnique(String clientName) {

        log.debug("Check clientName: {} on unique", clientName);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", clientName);

        return namedParameterJdbcTemplate.queryForObject(sqlCheckUniqueClientName, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Client client) {

        log.debug("Update client :{}", client);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", client.getClientName())
                .addValue("clientId", client.getClientId());

        return namedParameterJdbcTemplate.update(sqlUpdateClientName, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer clientId) {

        log.debug("delete client id:{}", clientId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientId", clientId);

        return namedParameterJdbcTemplate.update(sqlDeleteClientById, sqlParameterSource);
    }

    @Override
    public Integer count() {

        log.debug("count();");

        return namedParameterJdbcTemplate.queryForObject(sqlCountClient,new MapSqlParameterSource(),Integer.class);
    }

    private class ClientRowMapper implements RowMapper<Client>{
        @Override
        public Client mapRow(ResultSet resultSet, int i) throws SQLException {

            log.debug("Start : mapRow()");

            Client client = new Client(resultSet.getInt("client_id"),resultSet.getString("client_name"));
            return client;
        }
    }
}
