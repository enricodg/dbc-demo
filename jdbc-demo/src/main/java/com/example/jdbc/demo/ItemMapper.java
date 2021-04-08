package com.example.jdbc.demo;

import com.example.jdbc.demo.entity.Item;
import com.example.jdbc.demo.entity.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ItemMapper implements RowMapper<Item> {
  public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
    Item student = new Item();
    student.setId(rs.getLong("id"));
    student.setName(rs.getString("name"));
    student.setQty(rs.getInt("qty"));
    student.setPrice(rs.getDouble("price"));
    return student;
  }
}