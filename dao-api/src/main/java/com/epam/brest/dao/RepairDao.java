package com.epam.brest.dao;


import com.epam.brest.model.Repair;

import java.time.LocalDate;
import java.util.List;

/**
 *  Repair Dao Interface.
 */
public interface RepairDao {
    /**
     * Get all repairs .
     *
     * @return repair list.
     */
    List<Repair> findAll();

    /**
     * Get repair by Id.
     * @param repairId
     * @return Repair  with personal Id.
     */
    Repair getRepairById(Integer repairId);

    /**
     * Create repair.
     * @param repair
     * @return Integer  number of created repair.
     */
    Integer create(Repair repair);

    /**
     * Update repair.
     *
     * @param  repair
     * @return Integer  number of update repair.
     */
    Integer update(Repair repair);

    /**
     * Delete Repair with specific Id
     *
     * @param repairId Id of repair
     * @return Integer  number of delete repair.
     */
    Integer delete(Integer repairId);

    /**
     * Count Repair.
     *
     * @return Integer  number of  repair.
     */
    Integer count();

    /**
     * Filter repairs by dates.
     * @param startLimitDate - start  date of search limit.
     * @param endLimitDate - end date of search limit.
     * @return collection of repairs.
     */
    List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate);
}

