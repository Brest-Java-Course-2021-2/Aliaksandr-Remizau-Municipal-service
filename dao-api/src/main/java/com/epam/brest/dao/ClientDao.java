package com.epam.brest.dao;

import com.epam.brest.model.Client;

import java.util.List;

public interface ClientDao {

    List<Client> findAll();

    Integer create(Client client);

    Integer update(Client client);

    Integer delete(Integer clientId);
}
