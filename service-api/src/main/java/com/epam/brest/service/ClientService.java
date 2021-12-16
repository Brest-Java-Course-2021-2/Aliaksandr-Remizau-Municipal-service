package com.epam.brest.service;

import com.epam.brest.model.Client;

import java.util.List;

/**
 * Client Service Interface.
 */
public interface ClientService {

    /**
     * Get all clients .
     *
     * @return clients list.
     */
    List<Client> findAll();

    /**
     * Get client by Id.
     *
     * @return Client  with personal Id.
     */
    Client getClientById(Integer clientId);

    /**
     * Create client.
     *
     * @return Integer ID of create client.
     */
    Integer create(Client client);

    /**
     * Update client.
     *
     * @return Integer  number of update client.
     */
    Integer update(Client client);

    /**
     * Delete Client with specific Id
     *
     * @return Integer  number of delete client.
     */
    Integer delete(Integer clientId);

    /**
     * Count Client.
     *
     * @return Integer  number of  client.
     */
    Integer count();

}
