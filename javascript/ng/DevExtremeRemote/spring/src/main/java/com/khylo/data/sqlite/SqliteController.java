package com.khylo.data.sqlite;

import com.khylo.data.DataEntity;
import com.khylo.data.DataRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sqlite")
public class SqliteController {

    private final DataRepository dataRepository; // Your JPA repository
    private final EntityManagerFactory entityManagerFactory;
    public SqliteController(@Qualifier("sqliteEntityManagerFactory") EntityManagerFactory entityManagerFactory, DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

  @GetMapping
  public Page<DataEntity> getData(
    @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    @RequestParam(value = "search", required = false) String search, // Search parameter
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String description,
    @RequestParam(value = "sortField", required = false) String sortField,
    @RequestParam(value = "sortOrder", required = false) String sortOrder) {


    // Implement search and filtering logic here, using the parameters
    // Example (adapt as needed):
    Page<DataEntity> data;
    if (search != null && !search.isEmpty()) {
      data = dataRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search,search, pageable);
    } else if (name != null) {
      data = dataRepository.findByNameContainingIgnoreCase(name, pageable);
    } else if (description != null) {
      data = dataRepository.findByDescriptionContainingIgnoreCase(description, pageable);
    } else {
      data = dataRepository.findAll(pageable);
    }

    return data;

  }

    @GetMapping("/2")
    public Page<DataEntity> getData2(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "filterField", required = false) String filterField,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        // 1. Apply filtering if needed
        // Example (adapt to your filtering requirements):
        if (filterField != null && filterValue != null) {
            if (filterField.equals("name")) {
              pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
              return dataRepository.findByNameContaining(filterValue, pageable);
            } else if (filterField.equals("description")) {
              pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
              return dataRepository.findByDescriptionContaining(filterValue, pageable);
            }
            // ... handle other filter fields
        }

        // 2. If no filter or after filter, get the data
        var data = dataRepository.findAll(pageable);
        return data; // Or your custom query

    }
}
