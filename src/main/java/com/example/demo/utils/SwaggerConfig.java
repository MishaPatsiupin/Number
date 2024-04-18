package com.example.demo.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** The type Swagger config. */
@Configuration
public class SwaggerConfig {
  /**
   * Api open api.
   *
   * @return the open api
   */
  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .servers(List.of(new Server().url("https://localhost:8080")))
        .info(new Info().title("NumBeer"));
  }
}
