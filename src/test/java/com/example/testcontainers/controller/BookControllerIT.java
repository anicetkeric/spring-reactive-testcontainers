package com.example.testcontainers.controller;

import com.example.testcontainers.config.AbstractIntegrationTest;
import com.example.testcontainers.domain.Book;
import com.example.testcontainers.repository.BookRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookControllerIT extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    private final static String BASE_CONTROLLER_ENDPOINT = "/api/book";

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll().subscribe();
    }


    @Test
    void testGetAllBooks200() {
        var bookDocument = buildBookObj();
        bookRepository.save(bookDocument).subscribe();

        webTestClient.get()
                .uri(BASE_CONTROLLER_ENDPOINT)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Book.class)
                .hasSize(1)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().get(0).getId()).isNotNull();
                    assertThat(response.getResponseBody().get(0).getTitle()).isEqualTo(bookDocument.getTitle());
                    assertThat(response.getResponseBody().get(0).getDescription()).isEqualTo(bookDocument.getDescription());
                    assertThat(response.getResponseBody().get(0).getIsbn()).isEqualTo(bookDocument.getIsbn());
                    assertThat(response.getResponseBody().get(0).getPage()).isEqualTo(bookDocument.getPage());
                    assertThat(response.getResponseBody().get(0).getPrice()).isEqualTo(bookDocument.getPrice());
                    assertThat(response.getResponseBody().get(0).getLanguage()).isEqualTo(bookDocument.getLanguage());
                    assertThat(response.getResponseBody().get(0).getPublicationDate()).isEqualTo(bookDocument.getPublicationDate());
                });
    }



    @Test
    void testGetBookById200() {
        var bookDocument = buildBookObj();
        bookDocument.setId(new ObjectId().toString());
        bookRepository.save(bookDocument).subscribe();

        webTestClient.get()
                        .uri(MessageFormat.format("{0}/{1}", BASE_CONTROLLER_ENDPOINT, bookDocument.getId()))
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectHeader()
                        .contentType(MediaType.APPLICATION_JSON)
                        .expectBody(Book.class)
                        .consumeWith(response -> {
                            assertThat(response.getResponseBody()).isNotNull();
                            assertThat(response.getResponseBody().getId()).isNotNull();
                            assertThat(response.getResponseBody().getTitle()).isEqualTo(bookDocument.getTitle());
                            assertThat(response.getResponseBody().getDescription()).isEqualTo(bookDocument.getDescription());
                            assertThat(response.getResponseBody().getIsbn()).isEqualTo(bookDocument.getIsbn());
                            assertThat(response.getResponseBody().getPage()).isEqualTo(bookDocument.getPage());
                            assertThat(response.getResponseBody().getPrice()).isEqualTo(bookDocument.getPrice());
                            assertThat(response.getResponseBody().getLanguage()).isEqualTo(bookDocument.getLanguage());
                            assertThat(response.getResponseBody().getPublicationDate()).isEqualTo(bookDocument.getPublicationDate());
                        });
    }


    @Test
    void testGetBookById404() {
        var id = new ObjectId().toString();

        webTestClient.get()
                        .uri(MessageFormat.format("{0}/{1}", BASE_CONTROLLER_ENDPOINT, id))
                        .exchange()
                        .expectStatus()
                        .isNotFound()
                        .expectHeader()
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .expectBody()
                        .jsonPath("$.instance").isNotEmpty()
                        .jsonPath("$.type").isEqualTo("about:blank")
                        .jsonPath("$.title").isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value())
                        .jsonPath("$.detail").isEqualTo("Book id not found");
    }

    @Test
    void testAddBook() {
        var bookRequest = buildBookObj();

        webTestClient.post()
                .uri(BASE_CONTROLLER_ENDPOINT)
                .body(Mono.just(bookRequest), Book.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Book.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().getId()).isNotNull();
                    assertThat(response.getResponseBody().getTitle()).isEqualTo(bookRequest.getTitle());
                    assertThat(response.getResponseBody().getDescription()).isEqualTo(bookRequest.getDescription());
                    assertThat(response.getResponseBody().getIsbn()).isEqualTo(bookRequest.getIsbn());
                    assertThat(response.getResponseBody().getPage()).isEqualTo(bookRequest.getPage());
                    assertThat(response.getResponseBody().getPrice()).isEqualTo(bookRequest.getPrice());
                    assertThat(response.getResponseBody().getLanguage()).isEqualTo(bookRequest.getLanguage());
                    assertThat(response.getResponseBody().getPublicationDate()).isEqualTo(bookRequest.getPublicationDate());
                });
    }

    @Test
    void testUpdateBook200() {
        var bookDocument = buildBookObj();
        var bookRequest = buildBookObj();
        bookRequest.setPrice(500);
        bookRequest.setPage(250);
        bookRequest.setIsbn("78polkj");
        bookRequest.setTitle("update book");
        bookRequest.setDescription("update new book desc");
        bookDocument.setId(new ObjectId().toString());
        bookRepository.save(bookDocument).subscribe();

        webTestClient.put()
                .uri(MessageFormat.format("{0}/{1}", BASE_CONTROLLER_ENDPOINT, bookDocument.getId()))
                .body(Mono.just(bookRequest), Book.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Book.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().getId()).isNotNull();
                    assertThat(response.getResponseBody().getTitle()).isEqualTo(bookRequest.getTitle());
                    assertThat(response.getResponseBody().getDescription()).isEqualTo(bookRequest.getDescription());
                    assertThat(response.getResponseBody().getIsbn()).isEqualTo(bookRequest.getIsbn());
                    assertThat(response.getResponseBody().getPage()).isEqualTo(bookRequest.getPage());
                    assertThat(response.getResponseBody().getPrice()).isEqualTo(bookRequest.getPrice());
                    assertThat(response.getResponseBody().getLanguage()).isEqualTo(bookRequest.getLanguage());
                    assertThat(response.getResponseBody().getPublicationDate()).isEqualTo(bookRequest.getPublicationDate());
                });
    }



    @Test
    void testUpdateBook404() {
        var bookRequest = buildBookObj();
        var id = new ObjectId().toString();

        webTestClient.put()
                .uri(MessageFormat.format("{0}/{1}", BASE_CONTROLLER_ENDPOINT, id))
                .body(Mono.just(bookRequest), Book.class)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectHeader()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .expectBody()
                .jsonPath("$.instance").isNotEmpty()
                .jsonPath("$.type").isEqualTo("about:blank")
                .jsonPath("$.title").isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase())
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value())
                .jsonPath("$.detail").isEqualTo("Book id not found");
    }

    @Test
    void testDeleteBookById() {
        var bookDocument = buildBookObj();
        bookDocument.setId(new ObjectId().toString());
        bookRepository.save(bookDocument).subscribe();

        webTestClient.delete()
                .uri(MessageFormat.format("{0}/{1}", BASE_CONTROLLER_ENDPOINT, bookDocument.getId()))
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody();
    }


    private Book buildBookObj(){
        return Book.builder()
                .title("title")
                .description("book desc")
                .isbn("aswdc142")
                .page(101)
                .price(50)
                .language("en")
                .publicationDate(LocalDate.of(2024, 12, 12))
                .build();
    }

}