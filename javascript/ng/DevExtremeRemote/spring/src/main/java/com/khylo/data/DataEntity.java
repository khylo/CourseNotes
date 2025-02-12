package com.khylo.data;

import jakarta.persistence.*;

@Entity
@Table(name = "data_entity")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For H2 auto-increment
    private Long id;

    private String name;
    private String description;

    // Getters and setters (Important!)
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}

    // Constructor (if needed)
    public DataEntity() {} // Important for JPA

    public DataEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
