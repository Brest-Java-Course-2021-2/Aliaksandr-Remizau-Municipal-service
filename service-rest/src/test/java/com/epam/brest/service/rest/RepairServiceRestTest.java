package com.epam.brest.service.rest;


import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class RepairServiceRestTest {

    private final Logger log = LogManager.getLogger(RepairServiceRestTest.class);

    public static final LocalDate START_LIMIT_DATE = LocalDate.parse("2022-01-12");

    public static final LocalDate END_LIMIT_DATE = LocalDate.parse("2022-01-20");

    public static final String REPAIRS_URL = "http://localhost:8088/repairs";

    public static final String URL_FILTER = "http://localhost:8088/repairs/filter?startLimitDate=2022-01-12&endLimitDate=2022-01-20";



    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();


    RepairServiceRest repairServiceRest;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        repairServiceRest = new RepairServiceRest(REPAIRS_URL,restTemplate);
    }

    @Test
    void shouldFindAll() throws Exception {
        log.debug("shouldFindAll()");
        // GIVEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(createTestedRepair(0),
                                                                            createTestedRepair(1))))
                );
        //WHEN
        List<Repair> repairs = repairServiceRest.findAll();
        //THEN
        mockServer.verify();
        assertNotNull(repairs );
        assertFalse(repairs.isEmpty());
        assertTrue(repairs .size() == 2);
    }

    @Test
    void shouldGetRepairById() throws Exception {
        log.debug("getRepairById()");
        //GIVEN
        Integer id = 1;
        Repair repair = createTestedRepair(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(repair))
                );
        // WHEN
        Repair testRepair= repairServiceRest.getRepairById(id);
        //THEN
        mockServer.verify();
        assertNotNull(testRepair);
        assertEquals(testRepair.getRepairId(), id);
        assertEquals(testRepair.getRepairType(), repair.getRepairType());
        assertEquals(testRepair.getPreferenceDate(),repair.getPreferenceDate());
        assertEquals(testRepair.getDifficultyLevel(),repair.getDifficultyLevel());
        assertEquals(testRepair.getClientId(),repair.getClientId());
    }

    @Test
    void shouldCreate() throws Exception{
        log.debug("shouldCreate()");
        //GIVEN
        int index = 5;
        Repair repair = new Repair();
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("5"))
                );
        //WHEN
        Integer id = repairServiceRest.create(createTestedRepair(index));
        //THEN
        mockServer.verify();
        assertNotNull(id);
        assertEquals(id,index);

    }

    @Test
    void shouldUpdate() throws Exception{
        log.debug("shouldUpdate()");
        //GIVEN
        Integer id = 2;
        Repair repair = createTestedRepair(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("2"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL+ "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(repair))
                );
        //WHEN
        int result = repairServiceRest.update(repair);
        Repair testRepair= repairServiceRest.getRepairById(id);
        //THEN
        mockServer.verify();
        assertTrue(2 == result);
        assertNotNull(testRepair);
        assertEquals(testRepair.getRepairId(), id);
        assertEquals(testRepair.getRepairType(), repair.getRepairType());
        assertEquals(testRepair.getPreferenceDate(),repair.getPreferenceDate());
        assertEquals(testRepair.getDifficultyLevel(),repair.getDifficultyLevel());
        assertEquals(testRepair.getClientId(),repair.getClientId());
    }

    @Test
    void shouldDelete() throws Exception{
        log.debug("shouldDelete()");
        //GIVEN
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        //WHEN
        int result = repairServiceRest.delete(id);
        //THEN
        mockServer.verify();
        assertTrue(1 == result);
    }

    @Test
    void shouldCount() throws Exception {
        log.debug("shouldCount()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(REPAIRS_URL + "/count")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        //WHEN
        int result = repairServiceRest.count();

        //THEN
        mockServer.verify();
        assertTrue(result> 0);
    }
    @Test
    void filterRepairByPreferenceDate()throws Exception {
        log.debug("filterRepairByPreferenceDate()");
        //GIVEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_FILTER)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(createTestedRepair(1)))));

        //WHEN
        List<Repair> list = repairServiceRest.filterRepairByPreferenceDate(START_LIMIT_DATE, END_LIMIT_DATE);
        //THEN
        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() == 1);

    }
    private Repair createTestedRepair(int index) {
        Repair testedRepair = new Repair();
        LocalDate preferenceDate = LocalDate.parse("2022-01-15");
        testedRepair.setRepairId(index);
        testedRepair.setRepairType(RepairType.ANOTHER);
        testedRepair.setAddress("Moskovskaya 250-1" + index);
        testedRepair.setDifficultyLevel(LevelOfDifficulty.HARD);
        testedRepair.setPreferenceDate(preferenceDate.minusDays(index));
        testedRepair.setClientId(index + 1);

        return testedRepair;
    }
}