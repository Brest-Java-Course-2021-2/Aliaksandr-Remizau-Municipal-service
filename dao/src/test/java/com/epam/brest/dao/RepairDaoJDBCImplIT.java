package com.epam.brest.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
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
    void findAll() {
        log.debug("Execute test : findAll()");
        assertNotNull(repairDaoJDBC);
        assertNotNull(repairDaoJDBC.findAll());

    }

    @Test
    void getRepairById() {
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
}