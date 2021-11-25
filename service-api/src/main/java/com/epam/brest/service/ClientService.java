package com.epam.brest.service;

import com.epam.brest.model.Client;

public interface ClientService {

    Integer create(Client client);

    Integer count();

}
