package com.example.testcontainers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book")
public class Book {

    @Id
    private String id;

    private String title;

    private int page;

    private String isbn;

    private String description;

    private double price;

    private LocalDate publicationDate;

    private String language;
}
