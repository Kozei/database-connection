package com.example.demo;

import com.example.demo.repository.PersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(
//		basePackages = "com.example.demo",
//		excludeFilters = @ComponentScan.Filter(
//				type = FilterType.REGEX,
//				pattern = "com.example.demo.db2.*"
//		)
//)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//System.out.println(System.getProperty("java.class.path"));
		//Note that SpringApplication.exit() and System.exit() ensure that the JVM exits upon job completion
		//System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));

	}
}
