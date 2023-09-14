package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.example.demo.service")
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demo",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "com.example.demo.db2.*"
        )
)
public class JpaConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "dataSourceProperties")
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties pr = new DataSourceProperties();
        pr.setUrl(url);
        pr.setUsername(username);
        pr.setPassword(password);
        return pr;
    }
    @Value("${spring.datasource.hikari.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.datasource.hikari.leak-detection-threshold}")
    private int leakDetectionThreshold;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${custom.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Bean(name = "dataSource")
    public DataSource datasource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDatasource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        hikariDatasource.setJdbcUrl(jdbcUrl);
        hikariDatasource.setLeakDetectionThreshold(leakDetectionThreshold);
        hikariDatasource.setMaximumPoolSize(maximumPoolSize);
        hikariDatasource.setMinimumIdle(minimumIdle);
        hikariDatasource.setUsername(username);
        hikariDatasource.setPassword(password);

        return hikariDatasource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource datasource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPackagesToScan("com.example.demo.domain");
        em.setPersistenceUnitName("default");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", ddl);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}
