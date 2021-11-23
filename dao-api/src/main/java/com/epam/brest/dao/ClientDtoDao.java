package com.epam.brest.dao;

import com.epam.brest.model.dto.ClientDto;

import java.util.List;

/**
 * ClientDto DAO Interface.
 */
public interface ClientDtoDao {

    /**
     * Get all clients with number of their repairs.
     *
     * @return clients list.
     */
    List<ClientDto> findAllWithRepairs();
}

