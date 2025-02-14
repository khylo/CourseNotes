package com.khylo.data;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class JpaConfig {

  @Primary
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    EntityManagerFactoryBuilder builder, DataSource h2DataSource) {

    return builder
      .dataSource(h2DataSource)
      .packages("com.khylo.data")
      .persistenceUnit("h2")
      .build(); // No need to manually set properties!
  }

  @Primary
  @Bean
  public PlatformTransactionManager h2TransactionManager(
    LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory.getObject());
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean sqliteEntityManagerFactory(
    EntityManagerFactoryBuilder builder, DataSource sqliteDataSource) {
    return builder
      .dataSource(sqliteDataSource)
      .packages("com.khylo.data.sqlite") // Entities for SQLite
      .persistenceUnit("sqlite") // Persistence unit name
      .build();
  }

  @Bean
  public PlatformTransactionManager sqliteTransactionManager(
    LocalContainerEntityManagerFactoryBean sqliteEntityManagerFactory) {
    return new JpaTransactionManager(sqliteEntityManagerFactory.getObject());
  }

  // ... other configurations
}
