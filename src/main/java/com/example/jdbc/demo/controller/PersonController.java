package com.example.jdbc.demo.controller;

import com.example.jdbc.demo.Mapper;
import com.example.jdbc.demo.entity.Person;
import com.example.jdbc.demo.repository.PersonRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/persons")
@Log4j2
public class PersonController {

  @Autowired
  PersonRepository personRepository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  private Scheduler scheduler = Schedulers.newBoundedElastic(4, 4, "biasalah");

  @GetMapping
  public Flux<Person> findAll() {
    return Flux.defer(() -> {
      long instant = Instant.now().toEpochMilli();
      Person person = jdbcTemplate.query("SELECT * FROM person where id = " + randInt(1, 2000000), new Mapper()).stream().findFirst().get();
//      Person person = personRepository.findById((long) randInt(1, 2000000)).get();
      log.info("Time taken >> {} ms", Instant.now().toEpochMilli() - instant);
      return Mono.justOrEmpty(person);
    }).subscribeOn(scheduler);
  }

  @GetMapping("/insert")
  public List<Person> insert() {
    List<Person> persons = new ArrayList<>();
    for (int i = 0; i < 2000000; i++) {
      persons.add(Person.builder()
          .firstName(RandomString.make(10))
          .lastName(RandomString.make(10))
          .age(randInt(0, 80))
          .weight(randInt(0, 120))
          .height(randInt(0, 180))
          .build());
    }
    return personRepository.saveAll(persons);
  }

  public static int randInt(int min, int max) {
    Random rand = new Random();

    return rand.nextInt((max - min) + 1) + min;
  }
}
