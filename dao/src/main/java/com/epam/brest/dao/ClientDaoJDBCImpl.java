package com.epam.brest.dao;

import com.epam.brest.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


public class ClientDaoJDBCImpl implements ClientDao {

    private final Logger log = LogManager.getLogger(ClientDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_CLIENTS = "select c.client_id, c.client_name from client c order by c.client_name";
    private final String SQL_CLIENT_BY_ID = "select c.client_id, c.client_name from client c " +
            " where client_id = :clientId";
    private final String SQL_CREATE_CLIENT = "insert into client(client_name) values(:clientName)";
    private final String SQL_CHECK_UNIQUE_CLIENT_NAME = "select count(c.client_name) " +
            "from client c where lower(c.client_name) = lower(:clientName)";
    private final String SQL_UPDATE_CLIENT_NAME = "update client set client_name = :clientName " +
            "where client_id = :clientId";
    private final String SQL_DELETE_CLIENT_BY_ID = "delete from client where client_id = :clientId";
    private final String SQL_COUNT_CLIENT = "select count(*) from client";

    @Deprecated
    public ClientDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public ClientDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Client> findAll() {
        log.debug("Execute method: findAll");
        return namedParameterJdbcTemplate.query(SQL_ALL_CLIENTS, new ClientRowMapper());
    }

    @Override
    public Client getClientById(Integer clientId) {
        log.debug("getClientById id:{}", clientId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientId", clientId);
        return namedParameterJdbcTemplate.queryForObject(SQL_CLIENT_BY_ID, sqlParameterSource, new ClientRowMapper() {
        });
    }

    @Override
    public Integer create(Client client) {
        log.debug("Create client : {}", client);
        if (!isClientUnique(client.getClientName())) {
            log.warn("Client with the same name {} already exists.", client.getClientName());
            throw new IllegalArgumentException("Client with the same name already exists in DB.");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", client.getClientName().toUpperCase());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_CLIENT, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isClientUnique(String clientName) {
        log.debug("Check clientName: {} on unique", clientName);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", clientName);
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_UNIQUE_CLIENT_NAME, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Client client) {
        log.debug("Update client :{}", client);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientName", client.getClientName())
                .addValue("clientId", client.getClientId());
        return namedParameterJdbcTemplate.update(SQL_UPDATE_CLIENT_NAME, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer clientId) {
        log.debug("delete client id:{}", clientId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("clientId", clientId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_CLIENT_BY_ID, sqlParameterSource);
    }

    @Override
    public Integer count() {
        log.debug("count();");
        return namedParameterJdbcTemplate.queryForObject(SQL_COUNT_CLIENT,new MapSqlParameterSource(),Integer.class);
    }

    private class ClientRowMapper implements RowMapper<Client>{
        @Override
        public Client mapRow(ResultSet resultSet, int i) throws SQLException {
            log.debug("Start : mapRow()");
            Client client = new Client();
            client.setClientId(resultSet.getInt("client_id"));
            client.setClientName(resultSet.getString("client_name"));
            return client;
        }
    }
}
