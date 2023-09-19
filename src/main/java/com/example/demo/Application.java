package com.example.demo;

import com.example.demo.db2.config.SecondaryDataSourceConfig;
import com.example.demo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Map;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.demo.db2.config")
//@EnableJpaRepositories(
//		basePackages = "com.example.demo",
//		excludeFilters = @ComponentScan.Filter(
//				type = FilterType.REGEX,
//				pattern = "com.example.demo.db2.*"
//		)
//)

public class Application {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		Arrays.stream(context.getBeanDefinitionNames()).forEach(LOG::info);

		//printBeans();

//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecondaryDataSourceConfig.class);
//		System.out.println(context+ "-----------------------------------------------------------------------------------");
		//System.out.println(System.getProperty("java.class.path"));
		//Note that SpringApplication.exit() and System.exit() ensure that the JVM exits upon job completion
		//System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));

	}

}
