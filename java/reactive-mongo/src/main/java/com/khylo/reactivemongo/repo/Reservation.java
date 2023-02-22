package com.khylo.reactivemongo.repo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Reservation
 */
@Document
/**
 * Reservation
 */
public record Reservation(@Id Integer id, String name) {

}