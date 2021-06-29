package com.relias.example.integration;

import com.intuit.karate.junit5.Karate;
import com.relias.example.Application;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
public class TransactionTest {

    @Before
    public void setUp() {
    }

    @Karate.Test
    Karate testFullPath() {
        return Karate.run("classpath:karate/transaction.feature");
    }
}
