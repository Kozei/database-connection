package com.example.demo.db2.service.impl;

import com.example.demo.db2.repository.SecondaryPersonRepository;
import com.example.demo.db2.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class DataServiceImpl implements DataService {
    private final SecondaryPersonRepository personRepository;

    @Autowired
    public DataServiceImpl(SecondaryPersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @Override
    //@Scheduled(cron = "0 0 0 * * *")
    public void deleteOldData() {
        Instant threshold = Instant.now().minus(1,ChronoUnit.MINUTES);
        personRepository.deleteSecondaryPersonByTimestamp(threshold);
    }
}
