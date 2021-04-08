package com.example.r2dbc.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table("person")
public class Person extends BaseSQL<Long> {

  String firstName;
  String lastName;
  Integer age;
  Integer height;
  Integer weight;
}
