package dev.alexengrig.socnetgraphanalysis.repository;

import dev.alexengrig.socnetgraphanalysis.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PersonRepositoryTestContainerTest {

    @Container
    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.2.3")
            .withStartupTimeout(Duration.ofMinutes(5));
    @Autowired
    private PersonRepository personRepository;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
        registry.add("spring.data.neo4j.database", () -> "neo4j");
    }

    @Test
    void should_save_person() {
        Mono.just(Person.builder().id(1).build())
                .flatMap(personRepository::save)
                .as(StepVerifier::create)
                .assertNext(person -> assertEquals(1, person.getId()))
                .verifyComplete();
    }
}