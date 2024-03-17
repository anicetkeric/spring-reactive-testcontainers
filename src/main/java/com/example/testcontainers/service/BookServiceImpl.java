package com.example.testcontainers.service;

import com.example.testcontainers.domain.Book;
import com.example.testcontainers.exception.DataNotFoundException;
import com.example.testcontainers.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Flux<Book> getAllBook() {
        log.info("Find all books");
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> addBook(Book book) {
        log.info("addBook : {} ", book);
        return bookRepository.save(book)
                .log();
    }

    @Override
    public Mono<Book> getBookById(String id) {
        log.info("find book by id: {} ", id);
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException("Book id not found"))));
    }

    @Override
    public Mono<Book> updateBook(Book book, String id) {
        log.info("Update book: {} ", book);
        return bookRepository.findById(id)
                .flatMap(book1 -> {
                    book1.setTitle(book.getTitle());
                    book1.setIsbn(book.getIsbn());
                    book1.setLanguage(book.getLanguage());
                    book1.setDescription(book.getDescription());
                    book1.setPage(book.getPage());
                    book1.setPrice(book.getPrice());
                    book1.setPublicationDate(book.getPublicationDate());
                    return bookRepository.save(book1);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException("Book id not found"))));
    }

    @Override
    public Mono<Void> deleteBookById(String id) {
        log.info("delete book by id: {} ", id);
        return bookRepository.deleteById(id);
    }
}
