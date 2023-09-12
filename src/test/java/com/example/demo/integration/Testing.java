package com.example.demo.integration;

import com.example.demo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Testing {

    public final PersonService personService;
    @Autowired
    public Testing(PersonService personService) {
        this.personService = personService;
    }

    @Test
    public void testPersistence(){
        personService.savePerson();
    }
}
