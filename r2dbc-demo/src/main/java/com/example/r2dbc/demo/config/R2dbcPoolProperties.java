package com.example.r2dbc.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class R2dbcPoolProperties {

  private int maxSize;
  private int maxIdleTime;
  private int maxLifeTime;
  private int maxCreateConnectionTime;
  private int loopResourcesWorkerCount;
}
