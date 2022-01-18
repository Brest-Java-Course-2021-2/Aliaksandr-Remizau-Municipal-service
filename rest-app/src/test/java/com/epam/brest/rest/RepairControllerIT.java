package com.epam.brest.rest;

import com.epam.brest.model.Repair;
import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.rest.exception.CustomExceptionHandler.REPAIR_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class RepairControllerIT {

    private static final Logger log = LogManager.getLogger(RepairControllerIT.class);

    public static final String REPAIRS_ENDPOINT = "/repairs";
    @Autowired
    private RepairController repairController;
    @Autowired
    private CustomExceptionHandler customExceptionHandler;;

    ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();


    private MockMvc mockMvc;

    MockMvcRepairService mockRepairService = new MockMvcRepairService();
    private static Repair testedRepair;

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(repairController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldCreateRepair() throws Exception {
        log.debug("shouldCreateRepair()");
        Repair testedRepair = createTestedRepair(12);
        Integer id = mockRepairService.create(testedRepair);
        assertNotNull(id);
    }

    @Test
    public void shouldFailCreateRepairWithDuplicateParameters() throws Exception {

        log.debug("shouldFailCreateRepairWithDuplicateParameters()");

        Integer id = mockRepairService.create(createTestedRepair(2));
        assertNotNull(id);
        Repair secondRepair = createTestedRepair(2);

        MockHttpServletResponse response =
                mockMvc.perform(post(REPAIRS_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(secondRepair))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertTrue(response.getContentAsString().contains("Repair with equals parameters already exists in DB."));
    }

    @Test
    public void shouldFindAll() throws Exception {

        log.debug("shouldFindAll()");
        // GIVEN
        Repair testedRepair = createTestedRepair(3);
        Integer id = mockRepairService.create(testedRepair);
        assertNotNull(id);
        // WHEN
        List<Repair> repairs = mockRepairService.findAll();
        // THEN
        assertNotNull(repairs);
        assertTrue(repairs.size() > 0);
    }

    @Test
    void shouldFindRepairById() throws Exception {

        log.debug("shouldFindRepairById()");
        // GIVEN
        Repair testedRepair = createTestedRepair(2);
        Integer id = mockRepairService.create(testedRepair);
        assertNotNull(id);
        // WHEN
        Optional<Repair> optionalRepair = mockRepairService.findById(id);
        // THEN
        assertTrue(optionalRepair.isPresent());
        assertEquals(optionalRepair.get().getRepairId(), id);
        assertEquals(testedRepair.getRepairType(),optionalRepair.get().getRepairType());
        assertEquals(testedRepair.getAddress(),optionalRepair.get().getAddress());
        assertEquals(testedRepair.getDifficultyLevel(),optionalRepair.get().getDifficultyLevel());
        assertEquals(testedRepair.getPreferenceDate(),optionalRepair.get().getPreferenceDate());
        assertEquals(testedRepair.getClientId(),optionalRepair.get().getClientId());
    }
    @Test
    public void shouldReturnRepairNotFoundError() throws Exception {

        log.debug("shouldReturnRepairNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(REPAIRS_ENDPOINT + "/911")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(),REPAIR_NOT_FOUND);
    }

    @Test
    public void shouldUpdateRepair() throws Exception {
        log.debug("shouldUpdateRepair()");

        // GIVEN
        Repair testedRepair = createTestedRepair(4);
        Integer id = mockRepairService.create(testedRepair);
        assertNotNull(id);

        Optional<Repair> optionalRepair = mockRepairService.findById(id);
        assertTrue(optionalRepair.isPresent());

        optionalRepair.get().setRepairType(RepairType.ELECTRIC);
        optionalRepair.get().setAddress("Moskovskaya 250-13" );
        optionalRepair.get().setDifficultyLevel(LevelOfDifficulty.EASY);
        optionalRepair.get().setPreferenceDate(LocalDate.of(2022,7,12));
        optionalRepair.get().setClientId(1);
        // WHEN
        int result = mockRepairService.update(optionalRepair.get());
        // THEN
        assertTrue(1 == result);

        Optional<Repair> updatedOptionalRepair = mockRepairService.findById(id);
        assertTrue(updatedOptionalRepair.isPresent());
        assertEquals(updatedOptionalRepair.get().getRepairId(), id);
        assertEquals(updatedOptionalRepair.get().getRepairType(),optionalRepair.get().getRepairType());
        assertEquals(updatedOptionalRepair.get().getAddress(),optionalRepair.get().getAddress());
        assertEquals(updatedOptionalRepair.get().getDifficultyLevel(),optionalRepair.get().getDifficultyLevel());
        assertEquals(updatedOptionalRepair.get().getPreferenceDate(),optionalRepair.get().getPreferenceDate());
        assertEquals(updatedOptionalRepair.get().getClientId(),optionalRepair.get().getClientId());
    }

    @Test
    public void shouldDeleteRepair() throws Exception {
        log.debug("shouldDeleteRepair()");
        // GIVEN
        Repair testedRepair = createTestedRepair(7);
        Integer id = mockRepairService.create(testedRepair);
        assertNotNull(id);

        List<Repair> repairs = mockRepairService.findAll();
        assertNotNull(repairs);
        // WHEN
        int result = mockRepairService.delete(id);
        // THEN
        assertTrue(1 == result);

        List<Repair> afterDeleteRepair = mockRepairService.findAll();
        assertNotNull(afterDeleteRepair);
        assertTrue(repairs.size()-1 == afterDeleteRepair.size());
    }

    @Test
    void shouldFilterRepairByPreferenceDate() {
        log.debug("testFilterRepairByPreferenceDate()");

    }


    class MockMvcRepairService {

        public List<Repair> findAll() throws Exception {
            log.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(REPAIRS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Repair>>() {
            });
        }

        public Optional<Repair> findById(Integer id) throws Exception {

            log.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(REPAIRS_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Repair.class));
        }

        public Integer create(Repair repair) throws Exception {

            log.debug("create({})", repair);
            String json = objectMapper.writeValueAsString(repair);

            MockHttpServletResponse response =
                    mockMvc.perform(post(REPAIRS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Repair repair) throws Exception {

            log.debug("update({})", repair);
            MockHttpServletResponse response =
                    mockMvc.perform(put(REPAIRS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(repair))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer repairId) throws Exception {

            log.debug("delete(id:{})", repairId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(REPAIRS_ENDPOINT).append("/")
                                            .append(repairId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private List<Repair> filterRepairByPreferenceDate(LocalDate startLimitDate, LocalDate endLimitDate) throws Exception {
            log.debug("filterRepairByPreferenceDate({},{})", startLimitDate, endLimitDate);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.get(new StringBuilder(REPAIRS_ENDPOINT)
                                            .append("/filter").toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), List.class);
        }
    }
    private Repair createTestedRepair(int index) {
        Repair testedRepair = new Repair();
        LocalDate preferenceDate = LocalDate.parse("2021-12-31");
        testedRepair.setRepairId(index);
        testedRepair.setRepairType(RepairType.ANOTHER);
        testedRepair.setAddress("Moskovskaya 250-1" + index);
        testedRepair.setDifficultyLevel(LevelOfDifficulty.HARD);
        testedRepair.setPreferenceDate(preferenceDate.minusDays(index));
        testedRepair.setClientId(1);

        return testedRepair;
    }

}
