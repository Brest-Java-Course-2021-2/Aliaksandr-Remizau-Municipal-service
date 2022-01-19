package com.epam.brest.service.rest;

import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/**
 *  Client DTO Service Rest implementation.
 */
@Service
public class ClientDtoServiceRest implements ClientDtoService {
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(ClientDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;
    /**
     * Default constructor.
     */
    public ClientDtoServiceRest(){
    }

    public ClientDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ClientDto> findAllWithRepairs() {
        log.debug("findAllWithRepairs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<ClientDto>) responseEntity.getBody();
    }
}
