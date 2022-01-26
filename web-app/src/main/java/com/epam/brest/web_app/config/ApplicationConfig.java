package com.epam.brest.web_app.config;

import com.epam.brest.service.ClientDtoService;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.RepairService;
import com.epam.brest.service.rest.ClientDtoServiceRest;
import com.epam.brest.service.rest.ClientServiceRest;
import com.epam.brest.service.rest.RepairServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {
    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }
    @Bean
    ClientDtoService clientDtoService() {
        String url = String.format("%s://%s:%d/clients_dto", protocol, host, port);
        return new ClientDtoServiceRest(url, restTemplate());
    }

    @Bean
    ClientService clientService () {
        String url = String.format("%s://%s:%d/clients", protocol, host, port);
        return new ClientServiceRest(url, restTemplate());
    }

    @Bean
    RepairService repairService() {
        String url = String.format("%s://%s:%d/repairs", protocol, host, port);
        return new RepairServiceRest(url, restTemplate());
    }
}
