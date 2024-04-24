package com.example.demo.service;

import com.example.demo.entity.Fact;
import org.springframework.http.ResponseEntity;

/** The interface Fact service. */
public interface FactService {

  /**
   * Create fact fact.
   *
   * @param number the number
   * @param description the description
   * @return the fact
   */
  Fact createFact(long number, String description);

  /**
   * Find fact fact.
   *
   * @param description the description
   * @return the fact
   */
  Fact findFact(String description);

  /**
   * Gets fact by number id.
   *
   * @param number the number
   * @return the fact by number id
   */
  Fact getFactByNumberId(long number);
}
