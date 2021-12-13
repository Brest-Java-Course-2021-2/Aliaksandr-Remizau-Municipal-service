package com.epam.brest.dao;


import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RepairDaoJDBCImpl implements RepairDao{

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_REPAIRS =
            "select * from repair r order by r.preference_date";
    private final String SQL_GET_REPAIR_BY_ID =
            "select r.repair_id, r.repair_type, r.address, r.difficulty_level, r.preference_date, r.client_id from repair r where r.repair_id = :repairId";
    private final String SQL_CREATE_REPAIR =
            "insert into repair(repair_type, address, difficulty_level, preference_date, client_id) values(:repairType, :address, :difficultyLevel, :preferenceDate, :clientId)";
    private final String SQL_CHECK_UNIQUE_REPAIR =
            "select count(r.address) from repair r where r.repair_type = :repairType AND r.address = :address AND r.difficulty_level = :difficultyLevel AND r.preference_date = :preferenceDate AND r.client_id = :clientId";
    private final String SQL_UPDATE_REPAIR =
            "update repair set repair_type = :repairType, address = :address, difficulty_level = :difficultyLevel, preference_date = :preferenceDate, client_id = :clientId  where repair_id = :repairId ";
    private final String SQL_DELETE_REPAIR_BY_ID =
            "delete from repair where repair_id = :repairId";
    private final String SQL_FIND_REPAIR_BY_DATE_FROM_TO =
            "select * from repair where preference_date between :startLimitDate AND :endLimitDate order by preference_date";
    private final String SQL_COUNT_REPAIRS =
            "select count(*) from repair";

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

        log.debug("getRepairById({})",repairId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("repairId",repairId);
        return namedParameterJdbcTemplate.queryForObject(SQL_GET_REPAIR_BY_ID,sqlParameterSource,new RepairRowMapper());
    }

    @Override
    public Integer create(Repair repair) {

        log.debug("create({})",repair);

        if (!isRepairUnique(repair)) {
            log.warn("Repair {} with equals parameters already exists.", repair);
            throw new DuplicateEntityException("Repair with equals parameters already exists in DB.");
        }
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("repairId",repair.getRepairId())
                        .addValue("repairType",String.valueOf (repair.getRepairType()))
                        .addValue("address", repair.getAddress())
                        .addValue("difficultyLevel",String.valueOf( repair.getDifficultyLevel()))
                        .addValue("preferenceDate",repair.getPreferenceDate())
                        .addValue("clientId", repair.getClientId());

                KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_REPAIR, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isRepairUnique(Repair repair){

        log.debug("isRepairUnique({})",repair);

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("repairId",repair.getRepairId())
                        .addValue("repairType",String.valueOf (repair.getRepairType()))
                        .addValue("address", repair.getAddress())
                        .addValue("difficultyLevel",String.valueOf( repair.getDifficultyLevel()))
                        .addValue("preferenceDate",repair.getPreferenceDate())
                        .addValue("clientId", repair.getClientId());

        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_UNIQUE_REPAIR, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Repair repair) {

        log.debug("update({})",repair);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("repairId",repair.getRepairId())
                .addValue("repairType",String.valueOf (repair.getRepairType()))
                .addValue("address", repair.getAddress())
                .addValue("difficultyLevel",String.valueOf( repair.getDifficultyLevel()))
                .addValue("preferenceDate",repair.getPreferenceDate())
                .addValue("clientId", repair.getClientId());
        return namedParameterJdbcTemplate.update(SQL_UPDATE_REPAIR, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer repairId) {

        log.debug("delete({})",repairId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("repairId", repairId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_REPAIR_BY_ID, sqlParameterSource);
    }

    @Override
    public Integer count() {

        log.debug("count()");

        return namedParameterJdbcTemplate.queryForObject(SQL_COUNT_REPAIRS,new MapSqlParameterSource(),Integer.class);
    }

    @Override
    public List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate) {

        log.debug("filterRepairByPreferenceDate({}, {})",startLimitDate,endLimitDate);

         SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("startLimitDate", startLimitDate).addValue("endLimitDate", endLimitDate);

            return namedParameterJdbcTemplate.query(SQL_FIND_REPAIR_BY_DATE_FROM_TO, sqlParameterSource, new RepairRowMapper());
    }

    private class RepairRowMapper implements RowMapper<Repair> {
        @Override
        public Repair mapRow(ResultSet resultSet, int i) throws SQLException {

            log.debug("Start : mapRow()");

            Repair repair = new Repair();

            repair.setRepairId(resultSet.getInt("repair_id"));
            repair.setRepairType(RepairType.valueOf(resultSet.getString("repair_type")));
            repair.setAddress(resultSet.getString("address"));
            repair.setDifficultyLevel(LevelOfDifficulty.valueOf(resultSet.getString("difficulty_level")));
            repair.setPreferenceDate(resultSet.getDate("preference_date").toLocalDate());
            repair.setClientId(resultSet.getInt("client_id"));

            return repair;
        }
    }
}
