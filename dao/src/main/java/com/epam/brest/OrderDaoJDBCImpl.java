package com.epam.brest;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OrderDaoJDBCImpl implements OrderDao{

     private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Integer create(Order order) {
        return null;
    }

    @Override
    public Integer update(Order order) {
        return null;
    }

    @Override
    public Integer delete(Integer orderId) {
        return null;
    }
}
