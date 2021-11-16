package com.epam.brest;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ClientDaoJDBCImpl implements ClientDao {

     private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClientDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Integer create(Client client) {
        return null;
    }

    @Override
    public Integer update(Client client) {
        return null;
    }

    @Override
    public Integer delete(Integer client) {
        return null;
    }
}
