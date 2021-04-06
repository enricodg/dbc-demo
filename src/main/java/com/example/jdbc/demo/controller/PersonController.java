package com.example.jdbc.demo.controller;

import com.example.jdbc.demo.ItemMapper;
import com.example.jdbc.demo.PersonMapper;
import com.example.jdbc.demo.entity.Item;
import com.example.jdbc.demo.entity.Person;
import com.example.jdbc.demo.repository.ItemRepository;
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
  ItemRepository itemRepository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  private Scheduler scheduler = Schedulers.newBoundedElastic(10, 10, "biasalah");

  @GetMapping
  public Mono<Person> findAll() {
    return Mono.defer(() -> getPerson().flatMap(p -> getItem().map(i -> p)));
  }

  private Mono<Person> getPerson() {
    return Mono.defer(() -> {
      long instant = Instant.now().toEpochMilli();

      return Mono.just(jdbcTemplate
          .query("SELECT * FROM person where id = " + randInt(1, 2000000), new PersonMapper())
          .stream().findFirst().get())
          .doOnNext(
              data -> log
                  .info("Time taken get person >> {} ms", Instant.now().toEpochMilli() - instant));
    }).subscribeOn(scheduler);
  }

  private Mono<Item> getItem() {
    return Mono.defer(() -> {
      long instant = Instant.now().toEpochMilli();

      return Mono.just(jdbcTemplate
          .query("SELECT * FROM item where id = " + randInt(1, 1000000), new ItemMapper())
          .stream().findFirst().get())
          .doOnNext(
              data -> log
                  .info("Time taken get item >> {} ms", Instant.now().toEpochMilli() - instant));
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

  @GetMapping("/insert-item")
  public boolean insertItem() {
    List<Item> items = new ArrayList<>();
    for (int i = 0; i < 1000000; i++) {
      items.add(Item.builder()
          .name(RandomString.make(10))
          .qty(randInt(0, 80))
          .price((double) randInt(0, 120))
          .build());
    }
    itemRepository.saveAll(items);

    return true;
  }

  public static int randInt(int min, int max) {
    Random rand = new Random();

    return rand.nextInt((max - min) + 1) + min;
  }
}
