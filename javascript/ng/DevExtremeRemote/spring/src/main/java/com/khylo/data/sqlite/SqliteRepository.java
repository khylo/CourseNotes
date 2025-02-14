package com.khylo.data.sqlite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager = "sqliteTransactionManager")
public interface SqliteRepository extends JpaRepository<SqliteEntity, Long> {
  // Your repository methods
}
