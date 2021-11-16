package com.epam.brest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml","classpath*:test-db.xml"})
class ClientDaoJDBCImplTest {

    @Test
    void findAll() {
    }
}