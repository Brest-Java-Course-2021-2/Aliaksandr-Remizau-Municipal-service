package com.epam.brest.service.impl;


import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
public class ClientDtoServiceImplIT {

    @Autowired
    ClientDtoService clientDtoService;

    @Test
    public void shouldAllWithNumberOfRepairs() {
        List<ClientDto> clients = clientDtoService.findAllWithRepairs();
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
        assertTrue(clients.get(0).getNumberOfRepairs() > 0);
    }

}
