package com.epam.brest.dao;

import com.epam.brest.model.Client;

import java.util.List;
/**
 * Client DAO Interface.
 */

public interface ClientDao {
    /**
     * Get all clients .
     *
     * @return clients list.
     */

    List<Client> findAll();

    /**
     * Create client.
     *
     * @param  Client client.
     *
     * @return Integer  number of create client.
     */

    Integer create(Client client);

    /**
     * Update client.
     *
     * @param  Client client.
     *
     * @return Integer  number of update client.
     */

    Integer update(Client client);
    /**
     * Delete Client with specific Id
     *
     * @param  Integer  client Id.
     *
     * @return Integer  number of delete client.
     */

    Integer delete(Integer clientId);
}
