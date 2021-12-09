package com.epam.brest.dao;


import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.sun.jdi.request.DuplicateRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
@Rollback
class RepairDaoJDBCImplIT {

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImpl.class);

    private RepairDaoJDBCImpl repairDaoJDBC;

    public RepairDaoJDBCImplIT(@Autowired RepairDao repairDaoJDBC) {
        this.repairDaoJDBC = (RepairDaoJDBCImpl) repairDaoJDBC;
    }

    @Test
    void testFindAll() {

        log.debug("testFindAll()");
        assertNotNull(repairDaoJDBC);
        assertNotNull(repairDaoJDBC.findAll());

        List<Repair> repairs = repairDaoJDBC.findAll();

        assertNotNull(repairs);
        assertFalse(repairs.isEmpty());
    }

    @Test
    void testGetRepairById() {

        log.debug("testGetRepairById()");
        List<Repair> repairs = repairDaoJDBC.findAll();
        if (repairs.size() == 0) {
            repairDaoJDBC
                    .create(new Repair(RepairType.ELECTRIC,"MOSKOVSKAYA 250-13", LevelOfDifficulty.EASY, LocalDate.of(2021,12,25),1));
            repairs = repairDaoJDBC.findAll();
        }
        Repair repairSrc = repairs.get(0);
        Repair repairDst = repairDaoJDBC.getRepairById(repairSrc.getRepairId());
        assertEquals(repairSrc.getRepairId(), repairDst.getRepairId());
    }

    @Test
    void testCreate() {

        log.debug("testCreate()");
        assertNotNull(repairDaoJDBC);
        int repairsSizeBefore = repairDaoJDBC.count();

        Repair repair = new Repair(RepairType.ELECTRIC,"MOSKOVSKAYA 250-13", LevelOfDifficulty.EASY, LocalDate.of(2021,12,25),1);
        int newRepairId= repairDaoJDBC.create(repair);

        assertNotNull(newRepairId);
        assertEquals( repairsSizeBefore, repairDaoJDBC.count() - 1);


    }
    @Test
    void testTryToCreateEqualsRepair() {

        log.debug("testTryToCreateEqualsRepair()");

        assertNotNull(repairDaoJDBC);
        Repair repair = new Repair(RepairType.ELECTRIC,"MOSKOVSKAYA 250-13", LevelOfDifficulty.EASY, LocalDate.of(2021,12,25),1);

        assertThrows(DuplicateRequestException.class, () -> {
            repairDaoJDBC.create(repair);
            repairDaoJDBC.create(repair);
        });
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testCount(){

        log.debug("testCount()");
        assertNotNull(repairDaoJDBC);
        Integer amount = repairDaoJDBC.count();

        assertNotNull(amount);
        assertTrue(amount > 0);
        assertEquals(repairDaoJDBC.findAll().size(), amount);

    }
}