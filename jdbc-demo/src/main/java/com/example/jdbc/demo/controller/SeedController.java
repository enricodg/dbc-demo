package com.example.jdbc.demo.controller;

import com.example.jdbc.demo.entity.Item;
import com.example.jdbc.demo.entity.Person;
import com.example.jdbc.demo.repository.ItemRepository;
import com.example.jdbc.demo.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seed")
@Log4j2
public class SeedController {

  @Autowired
  PersonRepository personRepository;

  @Autowired
  ItemRepository itemRepository;

  @GetMapping("/person")
  public boolean insert() {
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
    personRepository.saveAll(persons);
    return true;
  }

  @GetMapping("/item")
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
