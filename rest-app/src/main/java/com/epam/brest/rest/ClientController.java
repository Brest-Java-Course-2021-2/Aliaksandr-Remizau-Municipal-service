package com.epam.brest.rest;

import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Client Rest Controller.
 */
@Tag(name = "Client Controller")
@RestController
public class ClientController {
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(ClientController.class);
    /**
     * Field clientService.
     */
    private final ClientService clientService;

    /**
     * Constructor.
     *
     * @param clientService clientService.
     */

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Find All clients.
     *
     * @return client's list in json.
     */
    @Operation(summary = "Get all clients")
    @GetMapping(value = "/clients")
    public final List<Client> findAll(){

        log.debug("findAll()");
        return clientService.findAll();
    }

    /**
     * Get client by id.
     *
     * @param id client.
     * @return client in json.
     */
    @Operation(summary = "Get client by its specific ID")
    @GetMapping(value = "/clients/{id}")
    public final Client getClientById(@PathVariable Integer id) {

        log.debug("getClientById({})",id);
        return clientService.getClientById(id);
    }

    /**
     * Create client.
     *
     * @param client .
     * @return id created client,HttpStatus.OK.
     */
    @Operation(summary = "Create new client")
    @PostMapping(path = "/clients", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createClient(@RequestBody Client client) {

        log.debug("createClient({})", client);
        Integer id = clientService.create(client);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update client.
     *
     * @param  client .
     * @return int result amount of updated client,HttpStatus.OK.
     */
    @Operation(summary = "Update client ")
    @PutMapping(value = "/clients", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateClient(@RequestBody Client client) {

        log.debug("updateClient({})", client);
        int result = clientService.update(client);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete client.
     *
     * @param  id .
     * @return int result amount of deleted client,HttpStatus.OK.
     */
    @Operation(summary = "Delete client ")
    @DeleteMapping(value = "/clients/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer id) {

        log.debug("deleteClient()");
        int result = clientService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
