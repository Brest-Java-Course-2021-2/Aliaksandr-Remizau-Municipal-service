package com.epam.brest.rest;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
/**
 * Client Dto Controller.
 */
@RestController
public class ClientDtoController {
    private static final Logger log = LogManager.getLogger(ClientDtoController.class);

    private final ClientDtoService clientDtoService;

    public ClientDtoController(ClientDtoService clientDtoService) {
        this.clientDtoService = clientDtoService;
    }

    @GetMapping(value = "/clients_dto")
    public final Collection<ClientDto> getClientById(@PathVariable Integer id){

        log.debug("getClientById()");
        return clientDtoService.findAllWithRepairs();
    }
}
