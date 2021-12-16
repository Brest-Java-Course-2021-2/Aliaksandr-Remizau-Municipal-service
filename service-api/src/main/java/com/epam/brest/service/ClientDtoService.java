package com.epam.brest.service;

import com.epam.brest.model.dto.ClientDto;
import java.util.List;

/**
 * ClientDto Service Interface.
 */
public interface ClientDtoService {
    /**
     * Get all clients with number of their repairs.
     *
     * @return clients list.
     */
    List<ClientDto> findAllWithRepairs();
}
