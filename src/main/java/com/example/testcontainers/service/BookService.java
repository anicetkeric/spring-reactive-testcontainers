package com.example.testcontainers.service;

import com.example.testcontainers.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Flux<Book> getAllBook();

    Mono<Book> addBook(Book book);

    Mono<Book> getBookById(String id);

    Mono<Book> updateBook(Book book, String id);

    Mono<Void> deleteBookById(String id);
}
