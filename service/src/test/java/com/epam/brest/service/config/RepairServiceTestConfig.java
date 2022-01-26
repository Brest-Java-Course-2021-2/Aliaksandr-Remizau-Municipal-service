package com.epam.brest.service.config;


import com.epam.brest.dao.RepairDao;
import com.epam.brest.dao.RepairDaoJDBCImpl;
import com.epam.brest.service.RepairService;
import com.epam.brest.service.impl.RepairServiceImpl;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepairServiceTestConfig extends SpringJdbcConfig {
    @Bean
    RepairDao repairDao(){
        return new RepairDaoJDBCImpl(namedParameterJdbcTemplate());
    }
    @Bean
    RepairService repairService(){
        return new RepairServiceImpl(repairDao());
    }
}
