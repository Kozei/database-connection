package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//System.out.println(System.getProperty("java.class.path"));
		//Note that SpringApplication.exit() and System.exit() ensure that the JVM exits upon job completion
		//System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));

	}

}
