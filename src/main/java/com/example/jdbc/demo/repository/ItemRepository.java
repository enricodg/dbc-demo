package com.example.jdbc.demo.repository;

import com.example.jdbc.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
