package com.example.jdbc.demo;

import com.example.jdbc.demo.entity.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PersonMapper implements RowMapper<Person> {
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    Person student = new Person();
    student.setId(rs.getLong("id"));
    student.setFirstName(rs.getString("first_name"));
    student.setLastName(rs.getString("last_name"));
    student.setAge(rs.getInt("age"));
    student.setHeight(rs.getInt("weight"));
    student.setWeight(rs.getInt("height"));
    return student;
  }
}