package com.example.r2dbc.demo.repository;

import com.example.r2dbc.demo.entity.Person;
import java.util.List;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<Person, Long> {

  Mono<List<Person>> findAllByIdIn(List<Long> ids);
}
