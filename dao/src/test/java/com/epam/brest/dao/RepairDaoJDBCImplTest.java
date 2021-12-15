package com.epam.brest.dao;

import com.epam.brest.model.Repair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepairDaoJDBCImplTest {

    static Repair repair;
    static List<Repair> repairSingletonList;
    static String sql;
    static Integer repairId;
    static Integer returnedRow;


    @InjectMocks
    private RepairDaoJDBCImpl repairDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LogManager.getLogger(RepairDaoJDBCImplTest.class);

    @Captor
    private ArgumentCaptor<RowMapper<Repair>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<GeneratedKeyHolder> captorKeyHolder;

    @BeforeAll
    public static void setUpClass(){
        repair = new Repair();
        repairSingletonList = Collections.singletonList(repair);
    }

    @BeforeEach
    void setUp() {
        String sql = "select something";
        repairId = 0;
        returnedRow = 1;

    }


    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }


    @Test
    public void test_findAll(){
        log.debug("test_findAll()");

        ReflectionTestUtils.setField(repairDaoJDBC,"sqlAllRepairs",sql);

        Mockito.when(namedParameterJdbcTemplate.query(
                        any(),
                        ArgumentMatchers.<RowMapper<Repair>>any()))
                .thenReturn(repairSingletonList);

        List<Repair> resultList = repairDaoJDBC.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(
                eq(sql),
                captorMapper.capture());

        RowMapper<Repair> repairRowMapper = captorMapper.getValue();

        assertNotNull(repairRowMapper);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertSame(repair,resultList.get(0));
    }

    @Test
    public void test_getRepairById() {
        log.debug("test_getRepairById()");

        ReflectionTestUtils.setField(repairDaoJDBC,"sqlGetRepairById",sql);

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                        any(),
                        ArgumentMatchers.<SqlParameterSource>any(),
                        ArgumentMatchers.<RowMapper<Repair>>any()))
                .thenReturn(repair);

        Repair result = repairDaoJDBC.getRepairById(repairId);

        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Repair> mapper = captorMapper.getValue();

        assertNotNull(source);
        assertNotNull(mapper);

        assertNotNull(result);
        assertSame(repair, result);
    }
//TODO fix test
//    @Test
//    void test_create() {
//        log.debug("test_create()");
//        ReflectionTestUtils.setField(repairDaoJDBC,"sqlCreateRepair",sql);
//
//        Mockito.when(namedParameterJdbcTemplate.update(
//                     any(),
//                        ArgumentMatchers.<SqlParameterSource>any(),
//                        ArgumentMatchers.<KeyHolder>any()))
//                .thenReturn(0);
//
//        Integer result = repairDaoJDBC.create(repair);
//
//        verify(namedParameterJdbcTemplate).
//                update(eq(sql),captorSource.capture(),captorKeyHolder.capture());
//
//        SqlParameterSource source = captorSource.getValue();
//        KeyHolder keyHolder = captorKeyHolder.getValue();
//
//        assertNotNull(source);
//        assertNotNull(keyHolder);
//        assertSame(null,valueOf(result));
//
//    }

    @Test
    void test_update() {
        log.debug("test_update()");
        ReflectionTestUtils.setField(repairDaoJDBC,"sqlUpdateRepair",sql);

        when(namedParameterJdbcTemplate.
                update(
                        any(),
                        ArgumentMatchers.<SqlParameterSource>any())).
                thenReturn(returnedRow);

        Integer result = repairDaoJDBC.update(repair);

        verify(namedParameterJdbcTemplate).update(
                eq(sql),
                captorSource.capture());

        SqlParameterSource source = captorSource.getValue();

        assertNotNull(result);
        assertNotNull(source);
        assertSame(returnedRow,result);

    }

    @Test
    void test_delete() {
        log.debug("test_delete()");

        ReflectionTestUtils.setField(repairDaoJDBC,"sqlDeleteRepairById",sql);

        when(namedParameterJdbcTemplate.
                update(
                        any(),
                        ArgumentMatchers.<SqlParameterSource>any())).
                thenReturn(returnedRow);

        Integer result = repairDaoJDBC.delete(repairId);
        verify(namedParameterJdbcTemplate).
                update(
                        eq(sql),
                        captorSource.capture());

        SqlParameterSource source = captorSource.getValue();

        assertNotNull(result);
        assertNotNull(source);
        assertSame(returnedRow,result);

    }

    @Test
    void count() {
        log.debug("test_count()");

        ReflectionTestUtils.setField(repairDaoJDBC,"sqlCountRepairs",sql);

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                     any(),
                     ArgumentMatchers.<SqlParameterSource>any(),
                     eq(Integer.class)))
                .thenReturn(repairSingletonList.size());

        Integer result = repairDaoJDBC.count();

        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(
                        eq(sql),
                        captorSource.capture(),
                        eq(Integer.class));

        SqlParameterSource source = captorSource.getValue();

        assertNotNull(source);
        assertNotNull(result);
        assertSame(repairSingletonList.size(), result);
    }

    @Test
    void test_filterRepairByPreferenceDate() {

        log.debug("test_filterRepairByPreferenceDate()");

        LocalDate startLimitDate = LocalDate.of(2021,12,15);
        LocalDate endLimitDate = LocalDate.of(2021,12,31);
        ReflectionTestUtils.setField(repairDaoJDBC,"sqlFindRepairByDateFromTo",sql);

        when(namedParameterJdbcTemplate.
                query(
                        any(),
                        ArgumentMatchers.<SqlParameterSource>any(),
                        ArgumentMatchers.<RowMapper<Repair>>any())).
                thenReturn(repairSingletonList);

        List<Repair> resultList = repairDaoJDBC.filterRepairByPreferenceDate(startLimitDate,endLimitDate);

        verify(namedParameterJdbcTemplate).query(
                eq(sql),
                captorSource.capture(),
                captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Repair> rowMapper = captorMapper.getValue();

        assertNotNull(resultList);
        assertNotNull(source);
        assertNotNull(rowMapper);
        assertSame(repairSingletonList,resultList);
        log.debug("result source " + source + "result list" + resultList + "rowMapper" + rowMapper);
    }
}