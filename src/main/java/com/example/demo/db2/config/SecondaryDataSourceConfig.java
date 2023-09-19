package com.example.demo.db2.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
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
@ComponentScan("com.example.demo.service")
@EnableJpaRepositories(
        basePackages = "com.example.demo.db2",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
@ConfigurationProperties(prefix = "custom.secondary-datasource")
public class SecondaryDataSourceConfig {
    private String url;
    private String username;
    private String password;
    @NestedConfigurationProperty
    private final CustomHikariProperties customHikariProperties;
    private final CustomJpaProperties customJpaProperties;

    public SecondaryDataSourceConfig(CustomHikariProperties customHikariProperties, CustomJpaProperties customJpaProperties) {
        this.customHikariProperties = customHikariProperties;
        this.customJpaProperties = customJpaProperties;
    }

    @Bean(name = "secondaryDataSourceProperties")
    public DataSourceProperties secondaryDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(url);
        properties.setUsername(username);
        properties.setPassword(password);
        return properties;
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(@Qualifier("secondaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDatasource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        hikariDatasource.setJdbcUrl(customHikariProperties.getJdbcUrl());
        hikariDatasource.setLeakDetectionThreshold(customHikariProperties.getLeakDetectionThreshold());
        hikariDatasource.setMaximumPoolSize(customHikariProperties.getMaximumPoolSize());
        hikariDatasource.setMinimumIdle(customHikariProperties.getMinimumIdle());
        hikariDatasource.setUsername(username);
        hikariDatasource.setPassword(password);

        return hikariDatasource;
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondaryDataSource);
        em.setPackagesToScan("com.example.demo.db2.domain");
        em.setPersistenceUnitName("secondary");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", customJpaProperties.getDdl());
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(secondaryEntityManagerFactory.getObject()));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
