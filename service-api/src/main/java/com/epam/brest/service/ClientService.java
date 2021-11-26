package com.epam.brest.service;

import com.epam.brest.model.Client;

public interface ClientService {

    Client getClientById(Integer clientId);

    Integer create(Client client);

    Integer update(Client client);

    Integer delete(Integer clientId);

    Integer count();

}
