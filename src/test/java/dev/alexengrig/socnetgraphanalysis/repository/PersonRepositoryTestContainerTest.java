package dev.alexengrig.socnetgraphanalysis.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.testcontainers.containers.Neo4jContainer;

@DataNeo4jTest
class PersonRepositoryTestContainerTest {

    private static Neo4jContainer<?> neo4jContainer;

    @BeforeAll
    static void startNeo4j() {
        neo4jContainer = new Neo4jContainer<>().withAdminPassword("tester");
        neo4jContainer.start();
    }

    @AfterAll
    static void stopNeo4j() {
        neo4jContainer.stop();
    }
}