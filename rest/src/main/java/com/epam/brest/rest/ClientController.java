package com.epam.brest.rest;

import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    private static final Logger log = LogManager.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/clients/{id}")
    public final Client getClientById(@PathVariable Integer id) {

        log.debug("getClientById({})",id);
        return clientService.getClientById(id);
    }

    @PostMapping(path = "/clients", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createClient(@RequestBody Client client) {

        log.debug("createClient({})", client);
        Integer id = clientService.create(client);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/clients", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateClient(@RequestBody Client client) {

        log.debug("updateClient({})", client);
        int result = clientService.update(client);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/clients/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer id) {

        int result = clientService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
