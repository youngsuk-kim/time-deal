package com.bread.timedeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeDealApplication {

  public static void main(String[] args) {
    SpringApplication.run(TimeDealApplication.class, args);
  }

}
