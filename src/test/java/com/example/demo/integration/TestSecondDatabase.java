package com.example.demo.integration;

import com.example.demo.db2.service.SecondaryPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSecondDatabase {
    public final SecondaryPersonService personService;

    @Autowired
    public TestSecondDatabase(SecondaryPersonService personService) {
        this.personService = personService;
    }


    @Test
    public void testPersistence(){
        personService.savePerson();
    }
}