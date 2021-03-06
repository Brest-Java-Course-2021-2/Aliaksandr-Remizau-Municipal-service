package com.epam.brest.rest;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Client Dto Controller.
 */
@RestController
@Tag(name = "ClientDtoController")
public class ClientDtoController {
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(ClientDtoController.class);
    /**
     * Field clientDtoService.
     */
    private final ClientDtoService clientDtoService;

    /**
     * Constructor ClientDtoController.
     *
     * @param clientDtoService .
     */

    public ClientDtoController(ClientDtoService clientDtoService) {
        this.clientDtoService = clientDtoService;
    }

    /**
     * Get Client Dto.
     *
     * @return Client Dto collection.
     */

    @GetMapping(value = "/clients_dto")
    @Operation(summary = "Get all clients DTO with count of repairs")
    public final List<ClientDto> getClientsDtoWithCountRepairs(){

        log.debug("getClientsDtoWithCountRepairs({})");
        return clientDtoService.findAllWithRepairs();
    }
}
