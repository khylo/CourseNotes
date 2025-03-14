package com.khylo.data;

// DataRepository.java (JPA Repository)

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> { // Long is the ID type

    // Example custom query (if needed) - adapt to your filtering needs
    @Query("SELECT d FROM DataEntity d WHERE d.name LIKE %:name%")
    Page<DataEntity> findByNameContaining(@Param("name") String name, Pageable pageable);


    // Example custom query (if needed) - adapt to your filtering needs
    @Query("SELECT d FROM DataEntity d WHERE d.description LIKE %:description%")
    Page<DataEntity> findByDescriptionContaining(@Param("description") String description, Pageable pageable);

  Page<DataEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
  Page<DataEntity> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
  Page<DataEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);


}
