package com.epam.brest.service.impl;

import com.epam.brest.dao.RepairDao;
import com.epam.brest.model.Repair;
import com.epam.brest.service.RepairService;
import com.epam.brest.service.impl.exceptions.RepairNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 *  Repair Service implementation.
 */
@Service
@Transactional
public class RepairServiceImpl implements RepairService {
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(RepairServiceImpl.class);
    /**
     * injection dependency RepairDao.
     */
    private final RepairDao repairDao;

    /**
     * Constructor with  NamedParameterJdbcTemplate.
     *
     * @param repairDao
     */
    public RepairServiceImpl(RepairDao repairDao) {
        this.repairDao = repairDao;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Repair> findAll() {
        log.debug("findAll()");
        return this.repairDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Repair getRepairById(Integer repairId) {
        log.debug("getRepairById({})",repairId);
        try {
            return this.repairDao.getRepairById(repairId);
        } catch (EmptyResultDataAccessException e) {
            throw new RepairNotFoundException(repairId);
        }
    }

    @Override
    @Transactional
    public Integer create(Repair repair) {
        log.debug("create({})",repair);
        return this.repairDao.create(repair);
    }

    @Override
    @Transactional
    public Integer update(Repair repair) {
        log.debug("update({})",repair);
        return this.repairDao.update(repair);
    }

    @Override
    @Transactional
    public Integer delete(Integer repairId) {
        log.debug("delete({})",repairId);
        return this.repairDao.delete(repairId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        log.debug("count()");
        return this.repairDao.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate) {
        log.debug("filterRepairByPreferenceDate({}, {})",startLimitDate,endLimitDate);
        return this.repairDao.filterRepairByPreferenceDate(startLimitDate,endLimitDate);
    }
}