package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource({"classpath:dao-sql.properties"})
public class RestApplication extends SpringBootServletInitializer {

    private static final Logger log = LogManager.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
