package dev.alexengrig.socnetgraphanalysis.controller;

import dev.alexengrig.socnetgraphanalysis.entity.Person;
import dev.alexengrig.socnetgraphanalysis.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Mono<Person> getPerson(Integer personId) {
        return personService.getById(personId);
    }

    @PostMapping
    public Mono<Person> createPerson(Integer personId) {
        return personService.createById(personId);
    }
}
