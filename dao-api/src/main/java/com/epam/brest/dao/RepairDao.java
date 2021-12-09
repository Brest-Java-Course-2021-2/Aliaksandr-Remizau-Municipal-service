package com.epam.brest.dao;

import com.epam.brest.model.Client;
import com.epam.brest.model.Repair;

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
     *
     * @return Repair  with personal Id.
     */
    Repair getRepairById(Integer repairId);

    /**
     * Create repair.
     *
     * @return Integer  number of created repair.
     */
    Integer create(Repair repair);

    /**
     * Update repair.
     *
     * @return Integer  number of update repair.
     */
    Integer update(Repair repair);

    /**
     * Delete Repair with specific Id
     *
     * @return Integer  number of delete repair.
     */
    Integer delete(Integer repairId);

    /**
     * Count Repair.
     *
     * @return Integer  number of  repair.
     */

    Integer count();
}

