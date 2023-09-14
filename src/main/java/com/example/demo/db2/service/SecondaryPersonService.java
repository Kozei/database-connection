package com.example.demo.db2.service;

import com.example.demo.db2.domain.SecondaryPerson;
import com.example.demo.db2.repository.SecondaryPersonRepository;
import org.springframework.stereotype.Service;

@Service
public class SecondaryPersonService {

    public final SecondaryPersonRepository secondaryPersonRepository;

    public SecondaryPersonService(SecondaryPersonRepository secondaryPersonRepository) {
        this.secondaryPersonRepository = secondaryPersonRepository;
    }
    public void savePerson(){
        SecondaryPerson secondaryPerson = new SecondaryPerson();
        secondaryPerson.setFirstName("george-secondDd");
        secondaryPerson.setLastName("leonidis");
        secondaryPersonRepository.save(secondaryPerson);
    }
}