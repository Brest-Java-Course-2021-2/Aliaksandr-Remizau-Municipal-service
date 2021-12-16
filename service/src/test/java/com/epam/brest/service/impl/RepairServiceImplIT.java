package com.epam.brest.service.impl;

import com.epam.brest.model.Client;
import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.RepairService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class RepairServiceImplIT {
    private final Logger log = LogManager.getLogger(RepairServiceImplIT.class);
    private static Repair testedRepair;

    @Autowired
    RepairService repairService;

    @BeforeEach
    void setUp() {
        assertNotNull(repairService);
        testedRepair = new Repair(RepairType.ELECTRIC,"some Test Address", LevelOfDifficulty.HARD, LocalDate.of(2021,12,31),1);
    }

    @Test
    void testFindAll() {
        log.debug("test_findAll()");
        assertNotNull(repairService.findAll());
        List<Repair> repairs = repairService.findAll();
        assertNotNull(repairs);
        assertFalse(repairs.isEmpty());
    }

    @Test
    void testGetRepairById() {
        log.debug("testGetRepairById()");
        Integer repairId = repairService.create(testedRepair);
        assertNotNull(repairId);
        Repair repairGetById = repairService.getRepairById(repairId);
        assertEquals(testedRepair.getRepairType(),repairGetById.getRepairType());
        assertEquals(testedRepair.getAddress(),repairGetById.getAddress());
        assertEquals(testedRepair.getClientId(),repairGetById.getClientId());
        assertEquals(testedRepair.getDifficultyLevel(),repairGetById.getDifficultyLevel());
    }

    @Test
    void create() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void count() {
    }

    @Test
    void filterRepairByPreferenceDate() {
    }
}