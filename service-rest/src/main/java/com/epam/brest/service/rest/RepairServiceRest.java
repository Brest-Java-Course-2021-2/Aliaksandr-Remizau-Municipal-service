package com.epam.brest.service.rest;

import com.epam.brest.model.Repair;
import com.epam.brest.service.RepairService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Service
public class RepairServiceRest implements RepairService {

    private final Logger log = LogManager.getLogger(RepairServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public RepairServiceRest(){}

    public RepairServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Repair> findAll() {
        log.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Repair>) responseEntity.getBody();
    }

    @Override
    public Repair getRepairById(Integer repairId) {
        log.debug("findById({})", repairId);
        ResponseEntity<Repair> responseEntity =
                restTemplate.getForEntity(url + "/" + repairId, Repair.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Repair repair) {
        log.debug("create({})",repair);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, repair, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Repair repair) {
        log.debug("update({})", repair);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Repair> entity = new HttpEntity<>(repair, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer repairId) {
        log.debug("delete({})", repairId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Repair> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + repairId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        log.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count" , Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate) {
        log.debug("filterRepairByPreferenceDate({},{})",startLimitDate,endLimitDate);
        ResponseEntity <List<Repair>> responseEntity = restTemplate.exchange(
                url + "/filter?startLimitDate={startLimitDate}&endLimitDate={endLimitDate}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                startLimitDate, endLimitDate);
        return  responseEntity.getBody();
    }
}
