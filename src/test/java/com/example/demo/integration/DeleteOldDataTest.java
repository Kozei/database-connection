package com.example.demo.integration;

import com.example.demo.db2.service.impl.DataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteOldDataTest {

    private final DataServiceImpl dataService;

    @Autowired
    public DeleteOldDataTest(DataServiceImpl dataService) {
        this.dataService = dataService;
    }

    @Test
    public void deleteOldData(){
        dataService.deleteOldData();
    }
}
