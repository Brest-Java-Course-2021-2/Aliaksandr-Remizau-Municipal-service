package com.epam.brest.service.rest;

import com.epam.brest.model.Client;
import com.epam.brest.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientServiceRest implements ClientService {

    private final Logger log = LogManager.getLogger(ClientServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ClientServiceRest(){}

    public ClientServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client getClientById(Integer clientId) {
        return null;
    }

    @Override
    public Integer create(Client client) {
        return null;
    }

    @Override
    public Integer update(Client client) {
        return null;
    }

    @Override
    public Integer delete(Integer clientId) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }
}
