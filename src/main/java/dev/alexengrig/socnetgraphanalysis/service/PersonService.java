package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.entity.Person;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<Person> getById(Integer personId);

    Mono<Person> createById(Integer personId);
}
