package com.example.jdbc.demo.repository;

import com.example.jdbc.demo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
