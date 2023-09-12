package com.example.demo;
import com.example.demo.service.PersonService;
import com.example.demo.integration.Testing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	@Autowired
	PersonService personService;

	@Test
	void contextLoads() {
		Testing test = new Testing(personService);
		test.testPersistence();
	}
}
