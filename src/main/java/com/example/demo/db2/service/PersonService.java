package com.example.demo.db2.service;

import com.example.demo.db2.domain.Person;
import com.example.demo.db2.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    public final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public void savePerson(){
        Person person = new Person();
        person.setFirstName("george");
        person.setLastName("leonidis");
        personRepository.save(person);
    }
}
