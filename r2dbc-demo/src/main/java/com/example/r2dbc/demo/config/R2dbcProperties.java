package com.example.r2dbc.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.r2dbc")
public class R2dbcProperties {

  private String password;
  private String database;
  private int port;
  private String username;
  private String host;
  private int connectTimeout;
  private R2dbcPoolProperties pool;
}
