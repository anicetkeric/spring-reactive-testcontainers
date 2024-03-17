package com.example.testcontainers.config;

import com.example.testcontainers.SpringReactiveTestcontainersApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = {SpringReactiveTestcontainersApplication.class})
@AutoConfigureWebTestClient
@ActiveProfiles("integrationtest")
@Testcontainers
public abstract class AbstractIntegrationTest {
    @Autowired
    public WebTestClient webTestClient;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.1"));
}