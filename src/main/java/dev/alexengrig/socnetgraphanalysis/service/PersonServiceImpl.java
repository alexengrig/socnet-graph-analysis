package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.entity.Person;
import dev.alexengrig.socnetgraphanalysis.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Mono<Person> getById(Integer personId) {
        return personRepository.findById(personId);
    }

    @Override
    public Mono<Person> createById(Integer personId) {
        Person person = Person.builder().id(personId).build();
        return personRepository.save(person);
    }
}
