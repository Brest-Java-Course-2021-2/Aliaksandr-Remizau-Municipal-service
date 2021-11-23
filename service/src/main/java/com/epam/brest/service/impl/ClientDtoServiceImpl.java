package com.epam.brest.service.impl;

import com.epam.brest.dao.ClientDtoDao;
import com.epam.brest.model.dto.ClientDto;
import com.epam.brest.service.ClientDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ClientDtoServiceImpl implements ClientDtoService {
    private final ClientDtoDao clientDtoDao;

    public ClientDtoServiceImpl(ClientDtoDao clientDtoDao) {
        this.clientDtoDao = clientDtoDao;
    }

    @Override
    public List<ClientDto> findAllWithRepairs() {
        return clientDtoDao.findAllWithRepairs();
    }
}
