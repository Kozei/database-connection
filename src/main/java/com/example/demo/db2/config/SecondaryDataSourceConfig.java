package com.example.demo.db2.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@EnableConfigurationProperties(DataSourceProperties.class)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demo.db2.repository", // Replace with your secondary repository package
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {
    @Value("${spring.secondary-datasource.url}")
    private String url;
    @Value("${spring.secondary-datasource.username}")
    private String username;
    @Value("${spring.secondary-datasource.password}")
    private String password;

    @Bean(name = "secondaryDataSourceProperties")
    public DataSourceProperties secondaryDataSourceProperties() {
        DataSourceProperties pr = new DataSourceProperties();
        pr.setUrl(url);
        pr.setUsername(username);
        pr.setPassword(password);
        return pr;
    }
    @Value("${spring.secondary-datasource.hikari.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.secondary-datasource.hikari.leak-detection-threshold}")
    private int leakDetectionThreshold;
    @Value("${spring.secondary-datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.secondary-datasource.hikari.minimum-idle}")
    private int minimumIdle;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(@Qualifier("secondaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDatasource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        hikariDatasource.setJdbcUrl(jdbcUrl);
        hikariDatasource.setLeakDetectionThreshold(leakDetectionThreshold);
        hikariDatasource.setMaximumPoolSize(maximumPoolSize);
        hikariDatasource.setMinimumIdle(minimumIdle);

        return hikariDatasource;
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondaryDataSource);
        em.setPackagesToScan("com.example.demo.db2.domain"); // Replace with your secondary entity package
        em.setPersistenceUnitName("secondary");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", ddl);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(secondaryEntityManagerFactory.getObject()));
    }
}
