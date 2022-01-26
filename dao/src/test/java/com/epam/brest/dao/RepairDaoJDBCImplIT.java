package com.epam.brest.dao;

import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({RepairDaoJDBCImpl.class})
@PropertySource({"classpath:dao-sql.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class RepairDaoJDBCImplIT {

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImpl.class);

    @Autowired
    private RepairDaoJDBCImpl repairDaoJDBC;

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

        assertThrows(DuplicateEntityException.class, () -> {
            repairDaoJDBC.create(repair);
            repairDaoJDBC.create(repair);
        });
    }

    @Test
    void testUpdate() {

        log.debug("testUpdate()");
        List<Repair> repairs = repairDaoJDBC.findAll();
        if(repairs.isEmpty()){
            Repair newRepair = new Repair();
            repairDaoJDBC.create(newRepair);
            repairs = repairDaoJDBC.findAll();
        }
        Repair repairSrc = repairs.get(0);
        repairSrc.setRepairId(1);
        repairSrc.setRepairType(RepairType.ELECTRIC);
        repairSrc.setAddress("MOSKOVSKAYA 243-18");
        repairSrc.setDifficultyLevel(LevelOfDifficulty.MEDIUM);
        repairSrc.setPreferenceDate(LocalDate.parse("2021-12-31"));
        repairSrc.setClientId(1);
        repairDaoJDBC.update(repairSrc);

        Repair repairDst = repairDaoJDBC.getRepairById(repairSrc.getRepairId());
        assertEquals(repairSrc.getRepairId(), repairDst.getRepairId());
        assertEquals(repairSrc.getRepairType(),repairDst.getRepairType());
        assertEquals(repairSrc.getAddress(),repairDst.getAddress());
        assertEquals(repairSrc.getDifficultyLevel(),repairDst.getDifficultyLevel());
        assertEquals(repairSrc.getPreferenceDate(),repairDst.getPreferenceDate());
        assertEquals(repairSrc.getClientId(),repairDst.getClientId());
    }

    @Test
    void testDelete() {

        log.debug("testDelete()");

        Repair newRepair = new Repair(RepairType.ELECTRIC,"MOSKOVSKAYA 250-13", LevelOfDifficulty.EASY, LocalDate.of(2021,12,25),1);
        repairDaoJDBC.create(newRepair);

        assertNotNull(repairDaoJDBC.findAll());
        List<Repair> repairs = repairDaoJDBC.findAll();

        assertFalse(repairs.isEmpty());

        repairDaoJDBC.delete(repairs.get(repairs.size() - 1).getClientId());
        assertEquals(repairs.size() - 1, repairDaoJDBC.findAll().size());
    }

    @Test
    public void testFilterRepairByPreferenceDateIfDatePresent(){
        log.debug("testFilterRepairByPreferenceDate()");

        assertNotNull(repairDaoJDBC);

        LocalDate startLimitDate= LocalDate.parse("2021-12-30");
        LocalDate endLimitDate =  LocalDate.parse("2021-12-31");
        assertNotNull(repairDaoJDBC.filterRepairByPreferenceDate(startLimitDate,endLimitDate));
        List<Repair> repairs = repairDaoJDBC.filterRepairByPreferenceDate(startLimitDate,endLimitDate);

        assertFalse(repairs.isEmpty());
        assertEquals(2,repairs.size());
    }

    @Test
    public void testFilterRepairByPreferenceDateIfAllDateIsNull(){
        List<Repair> repairs = repairDaoJDBC.filterRepairByPreferenceDate(null,null);
        assertNotNull(repairs);
        assertEquals(0,repairs.size());
    }

    @Test
    public void testFilterRepairByPreferenceDateIfFirstDateIsNull(){
        LocalDate startLimitDate= LocalDate.parse("2021-12-30");
        List<Repair> repairs = repairDaoJDBC.filterRepairByPreferenceDate(startLimitDate,null);
        assertNotNull(repairs);
        assertEquals(0,repairs.size());
    }

    @Test
    public void testFilterRepairByPreferenceDateIfSecondDateIsNull(){
        LocalDate endLimitDate =  LocalDate.parse("2021-12-31");
        List<Repair> repairs = repairDaoJDBC.filterRepairByPreferenceDate(null,endLimitDate);
        assertNotNull(repairs);
        assertEquals(0,repairs.size());
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