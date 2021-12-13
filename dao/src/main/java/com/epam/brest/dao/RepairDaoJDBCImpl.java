package com.epam.brest.dao;

import com.epam.brest.dao.exception.DuplicateEntityException;
import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
/**
 *  Repair DAO implementation.
 */
public class RepairDaoJDBCImpl implements RepairDao{
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(RepairDaoJDBCImpl.class);
    /**
     * NamedParameterJdbcTemplate.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * SQL query for select all repairs.
     */
    @Value("${SQL_ALL_REPAIRS}")
    private  String sqlAllRepairs;

    /**
     * SQL query for select repair by ID.
     */
    @Value("${SQL_GET_REPAIR_BY_ID}")
    private  String sqlGetRepairById;

    /**
     * SQL query for create repair.
     */
    @Value("${SQL_CREATE_REPAIR}")
    private  String sqlCreateRepair;

    /**
     * SQL query for check unique repair.
     */
    @Value("${SQL_CHECK_UNIQUE_REPAIR}")
    private  String sqlCheckUniqueRepair;

    /**
     * SQL query for update repair.
     */
    @Value("${SQL_UPDATE_REPAIR}")
    private  String sqlUpdateRepair;

    /**
     * SQL query for delete repair by ID.
     */
    @Value("${SQL_DELETE_REPAIR_BY_ID}")
    private  String sqlDeleteRepairById ;

    /**
     * SQL query for find repair depend on LocalDate.
     */
    @Value("${SQL_FIND_REPAIR_BY_DATE_FROM_TO}")
    private  String sqlFindRepairByDateFromTo ;

    /**
     * SQL query for count repairs.
     */
    @Value("${SQL_COUNT_REPAIRS}")
    private  String sqlCountRepairs;

    /**
     * Constructor with  NamedParameterJdbcTemplate.
     *
     * @param namedParameterJdbcTemplate
     */
    public RepairDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Repair> findAll() {

        log.debug("Execute findAll()");
        return namedParameterJdbcTemplate.query(sqlAllRepairs,new RepairRowMapper());
    }

    @Override
    public Repair getRepairById(Integer repairId) {

        log.debug("getRepairById({})",repairId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("repairId",repairId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetRepairById,sqlParameterSource,new RepairRowMapper());
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
        namedParameterJdbcTemplate.update(sqlCreateRepair, sqlParameterSource, keyHolder);
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

        return namedParameterJdbcTemplate.queryForObject(sqlCheckUniqueRepair, sqlParameterSource, Integer.class) == 0;
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
        return namedParameterJdbcTemplate.update(sqlUpdateRepair, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer repairId) {

        log.debug("delete({})",repairId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("repairId", repairId);
        return namedParameterJdbcTemplate.update(sqlDeleteRepairById, sqlParameterSource);
    }

    @Override
    public Integer count() {

        log.debug("count()");

        return namedParameterJdbcTemplate.queryForObject(sqlCountRepairs,new MapSqlParameterSource(),Integer.class);
    }

    @Override
    public List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate) {

        log.debug("filterRepairByPreferenceDate({}, {})",startLimitDate,endLimitDate);

         SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("startLimitDate", startLimitDate).addValue("endLimitDate", endLimitDate);

            return namedParameterJdbcTemplate.query(sqlFindRepairByDateFromTo, sqlParameterSource, new RepairRowMapper());
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
