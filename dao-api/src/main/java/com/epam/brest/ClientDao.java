package com.epam.brest;

import java.util.List;

public interface ClientDao {

    List<Client>findAll();

    Integer create(Client client);

    Integer update(Client client);

    Integer delete(Integer clientId);
}
