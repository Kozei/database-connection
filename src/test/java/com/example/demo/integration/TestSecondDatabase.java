package com.example.demo.integration;

import com.example.demo.db2.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSecondDatabase {

    public final PersonService personService;

    @Autowired
    public TestSecondDatabase(PersonService personService) {
        this.personService = personService;
    }

    @Test
    public void testPersistence(){
        personService.savePerson();
    }
}
