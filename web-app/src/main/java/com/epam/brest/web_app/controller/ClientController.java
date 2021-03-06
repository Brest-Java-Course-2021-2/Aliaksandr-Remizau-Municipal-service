package com.epam.brest.web_app.controller;

import com.epam.brest.model.Client;
import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import com.epam.brest.service.ClientService;
import com.epam.brest.web_app.validators.ClientValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




/**
 * Client controller.
 */
@Controller
public class ClientController {
    private static final Logger log = LogManager.getLogger(ClientController.class);

    private final ClientValidator clientValidator;

    private final ClientDtoService clientDtoService;

    private final ClientService clientService;

    /**
     * Constructor with  clientId and client name.
     *
     * @param clientValidator
     * @param clientDtoService
     * @param clientService
     */
    public ClientController(ClientValidator clientValidator, ClientDtoService clientDtoService, ClientService clientService) {
        this.clientValidator = clientValidator;
        this.clientDtoService = clientDtoService;
        this.clientService = clientService;
    }

    /**
     * Go to clients list page.
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/clients")
    public final String clients(Model model) {
        log.debug("go to page clients");
        model.addAttribute("clients", clientDtoService.findAllWithRepairs());
        return "clients";
    }

    /**
     * Go to edit client page.
     *
     * @param id client's ID
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/client/{id}")
    public final String gotoEditClientPage(@PathVariable Integer id, Model model) {
        log.debug("gotoEditClientPage(id:{},model:{}", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("client", clientService.getClientById(id));
        return "client";
    }

    /**
     * Go to new client page.
     *
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/client")
    public final String gotoAddClientPage(Model model) {
        log.debug("gotoAddClientPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("client", new Client());
        return "client";
    }
    /**
     * Persist new client into persistence storage.
     *
     * @param client new client with filled data.
     * @return view name
     */
    @PostMapping(value = "/client")
    public String addClient(Client client, BindingResult result) {
        log.debug("addClient({}){}", client,result);
        clientValidator.validate(client,result);
        if(result.hasErrors()){
            return "client";
        }
        this.clientService.create(client);
        return "redirect:/clients";
    }

    /**
     * Update client.
     *
     * @param client department with filled data.
     * @return view name
     */
    @PostMapping(value = "/client/{id}")
    public String updateClient(Client client,BindingResult result) {

        log.debug("updateClient({})", client);

        clientValidator.validate(client,result);
        if(result.hasErrors()){
            return "client";
        }
        this.clientService.update(client);
        return "redirect:/clients";
    }
    /**
     * Delete client.
     *
     * @param id client's ID
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/client/{id}/delete")
    public final String deleteClientById(@PathVariable Integer id, Model model) {

        log.debug("delete({},{})", id, model);
        clientService.delete(id);
        return "redirect:/clients";
    }
}

