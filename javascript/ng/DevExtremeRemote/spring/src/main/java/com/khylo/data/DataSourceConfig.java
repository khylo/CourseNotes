package com.khylo.data;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

  @Bean
  @Primary // H2 DataSource
  @ConfigurationProperties("spring.datasource") // H2 properties
  public DataSource h2DataSource(DataSourceProperties properties) {
    // ... (Your existing H2 DataSource configuration)
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setUrl(properties.getUrl());
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    return dataSource;
  }

  @Bean
  @ConfigurationProperties("spring.datasource.sqlite") // SQLite properties
  public DataSource sqliteDataSource(DataSourceProperties properties) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(properties.getDriverClassName()); // org.sqlite.JDBC
    dataSource.setUrl(properties.getUrl()); // jdbc:sqlite:/path/to/your/sqlite.db
    return dataSource; // No username/password needed for SQLite files
  }

  // ... (EntityManagerFactory and TransactionManager configurations - see next step)
}
