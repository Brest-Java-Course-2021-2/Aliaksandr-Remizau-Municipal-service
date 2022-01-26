package com.epam.brest.service.impl;

import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.epam.brest.service.RepairService;
import com.epam.brest.service.config.RepairServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({RepairServiceTestConfig.class})
@PropertySource({"classpath:dao-sql.properties"})
@Transactional
@Rollback
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
    void testCreate() {
        log.debug("testCreate()");
        Integer sizeBefore = repairService.count();
        Integer testedRepairId = repairService.create(testedRepair);
        assertNotNull(testedRepairId);
        assertEquals(sizeBefore,repairService.count() - 1);

    }

    @Test
    void testUpdate() {
        log.debug("testUpdate()");
        Integer testedRepairId = 2;
        Repair repairSrc = repairService.getRepairById(testedRepairId);
        repairSrc.setAddress(repairSrc.getAddress() + "test");
        repairSrc.setPreferenceDate(repairSrc.getPreferenceDate().minusDays(1));
        repairSrc.setClientId(repairSrc.getClientId() + 1);

        repairService.update(repairSrc);
        Repair repairDst = repairService.getRepairById(repairSrc.getRepairId());

        assertEquals(repairSrc.getAddress(),repairDst.getAddress());
        assertEquals(repairSrc.getClientId(),repairDst.getClientId());
        assertEquals(repairSrc.getPreferenceDate(),repairDst.getPreferenceDate());
    }

    @Test
    void testDelete() {
        log.debug("testDelete()");
        List<Repair> repairs = repairService.findAll();
        assertNotNull(repairs);
        assertFalse(repairs.isEmpty());
        repairService.delete(repairs.get(0).getRepairId());
        assertEquals(repairService.findAll().size(),repairs.size() - 1);
    }

    @Test
    void testCount() {
        log.debug("testCount()");
        Integer count = repairService.count();
        assertNotNull(count);
        assertEquals(repairService.findAll().size(),count);
    }

    @Test
    void testFilterRepairByPreferenceDate() {
        log.debug("testFilterRepairByPreferenceDate()");
        LocalDate startLimitDate= LocalDate.parse("2021-12-20");
        LocalDate endLimitDate =  LocalDate.parse("2021-12-31");
        List<Repair> filteredRepairs = repairService.filterRepairByPreferenceDate(startLimitDate,endLimitDate);
        assertNotNull(filteredRepairs);
        assertFalse(filteredRepairs.isEmpty());
        assertEquals(5,filteredRepairs.size());
        assertEquals(startLimitDate,filteredRepairs.get(0).getPreferenceDate());
        assertEquals(endLimitDate.minusDays(1),filteredRepairs.get(3).getPreferenceDate());
    }
}