package com.battre.gwsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class GwSvcApplication {
  public static void main(String[] args) {
    SpringApplication.run(GwSvcApplication.class, args);
  }
}
