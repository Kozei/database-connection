package com.example.demo.service;

import com.example.demo.domain.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    public final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public void savePerson(){
        Person person = new Person();
        person.setFirstName("george-firstDb");
        person.setLastName("leonidis");
        personRepository.save(person);
    }
}
