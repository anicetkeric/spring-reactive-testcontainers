package com.example.testcontainers.controller;

import com.example.testcontainers.domain.Book;
import com.example.testcontainers.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public Flux<Book> getAllBooks() {
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public Mono<Book> getBookById(@PathVariable("id") String id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> addBook(@RequestBody @Valid Book book) {
        return bookService.addBook(book);

    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Book> updateBook(@RequestBody Book book, @PathVariable String id) {
        return bookService.updateBook(book, id);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBookById(@PathVariable String id){
        return bookService.deleteBookById(id);
    }

}
