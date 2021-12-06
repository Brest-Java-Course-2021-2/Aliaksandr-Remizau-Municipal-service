package com.epam.brest.dao;

import com.epam.brest.model.Repair;
import com.epam.brest.model.type.RepairType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RepairDaoJDBCImpl implements RepairDao{

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_REPAIRS =
            "select * from repair r order by r.preference_date";

    public RepairDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Repair> findAll() {
        log.debug("Execute findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_REPAIRS,new RepairRowMapper());
    }

    @Override
    public Repair getRepairById(Integer repairId) {
        return null;
    }

    @Override
    public Integer create(Repair repair) {
        return null;
    }

    @Override
    public Integer update(Repair repair) {
        return null;
    }

    @Override
    public Integer delete(Integer repairId) {
        return null;
    }

    private class RepairRowMapper implements RowMapper<Repair> {
        @Override
        public Repair mapRow(ResultSet resultSet, int i) throws SQLException {
            log.debug("Start : mapRow()");
            Repair repair = new Repair();
            repair.setRepairId(resultSet.getInt("repair_id"));
            repair.setRepairType(RepairType.valueOf(resultSet.getString("repair_type")));
            repair.
            return repair;
        }
    }

}
