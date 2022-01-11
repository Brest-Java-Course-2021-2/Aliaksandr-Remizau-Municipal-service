package com.epam.brest.service.impl.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class RepairNotFoundException extends EmptyResultDataAccessException {
    public RepairNotFoundException(Integer id) {
        super("Repair not found for id: " + id, 1);
    }
}
