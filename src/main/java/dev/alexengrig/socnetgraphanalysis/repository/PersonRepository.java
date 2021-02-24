package dev.alexengrig.socnetgraphanalysis.repository;

import dev.alexengrig.socnetgraphanalysis.entity.Person;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveNeo4jRepository<Person, Integer> {
}
