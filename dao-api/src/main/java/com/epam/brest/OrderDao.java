package com.epam.brest;

import java.util.List;

public interface OrderDao {

    List<Order>findAll();

    Integer create(Order order);

    Integer update(Order order);

    Integer delete(Integer orderId);
}
