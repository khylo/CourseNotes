package com.khylo.data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final DataRepository dataRepository; // Your JPA repository

    public DataController(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @GetMapping
    public Page<DataEntity> getData(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "filterField", required = false) String filterField,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        // 1. Apply filtering if needed
        // Example (adapt to your filtering requirements):
        // if (filterField != null && filterValue != null) {
        //     if (filterField.equals("name")) {
        //       pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
        //       return dataRepository.findByNameContaining(filterValue, pageable);
        //     }
        //     // ... handle other filter fields
        // }

        // 2. If no filter or after filter, get the data
        return dataRepository.findAll(pageable); // Or your custom query

    }
}
