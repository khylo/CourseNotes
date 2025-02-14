package com.khylo.data.sqlite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sqlite_table") // The actual table name in your SQLite DB
public class SqliteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
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
    public SqliteEntity() {} // Important for JPA

    public SqliteEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
