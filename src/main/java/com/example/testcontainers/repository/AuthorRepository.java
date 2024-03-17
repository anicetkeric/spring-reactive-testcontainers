package com.example.testcontainers.repository;

import com.example.testcontainers.domain.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Book, String> {
}
