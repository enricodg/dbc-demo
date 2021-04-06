package com.example.r2dbc.demo.controller;

import com.example.r2dbc.demo.entity.Item;
import com.example.r2dbc.demo.entity.Person;
import com.example.r2dbc.demo.repository.PersonRepository;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
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
  DatabaseClient databaseClient;

  private Scheduler scheduler = Schedulers.newBoundedElastic(10, 10, "biasalah");

  @GetMapping
  public Mono<Person> findAll() {
    return Mono.just(randInt(1, 2000000))
        .flatMap(id -> {
          long instant = Instant.now().toEpochMilli();

          return databaseClient.sql("SELECT * FROM person WHERE id = :id")
              .bind("id", id)
              .map(MAPPING_FUNCTION).first()
              .doOnNext(
                  s -> log.info("Time taken person >> {} ms", Instant.now().toEpochMilli() - instant))
              .doOnError(log::error);
        }).flatMap(person -> {
          long instant = Instant.now().toEpochMilli();

          return databaseClient.sql("SELECT * FROM item WHERE id = :id")
              .bind("id", randInt(1, 1000000))
              .map(MAPPING_FUNCTION_ITEM).first()
              .doOnNext(
                  s -> log.info("Time taken item >> {} ms", Instant.now().toEpochMilli() - instant))
              .doOnError(log::error)
              .map(item -> person);
        });
  }

  public static final BiFunction<Row, RowMetadata, Person> MAPPING_FUNCTION = (row, rowMetaData) -> Person
      .builder()
      .id(row.get("id", Long.class))
      .firstName(row.get("first_name", String.class))
      .lastName(row.get("last_name", String.class))
      .weight(row.get("weight", Integer.class))
      .height(row.get("height", Integer.class))
      .age(row.get("age", Integer.class))
      .build();

  public static final BiFunction<Row, RowMetadata, Item> MAPPING_FUNCTION_ITEM = (row, rowMetaData) -> Item
      .builder()
      .id(row.get("id", Long.class))
      .name(row.get("name", String.class))
      .qty(row.get("qty", Integer.class))
      .price(row.get("price", Double.class))
      .build();

  public static int randInt(int min, int max) {
    Random rand = new Random();

    return rand.nextInt((max - min) + 1) + min;
  }
}
