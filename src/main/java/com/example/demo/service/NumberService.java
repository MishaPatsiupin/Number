package com.example.demo.service;

import com.example.demo.entity.Numbeer;
import org.springframework.http.ResponseEntity;

/** The interface Number service. */
public interface NumberService {
  /**
   * Add number response entity.
   *
   * @param numberData the number data
   * @return the response entity
   */
  ResponseEntity<String> addNumber(long numberData);

  /**
   * Del number response entity.
   *
   * @param number the number
   * @return the response entity
   */
  ResponseEntity<String> delNumber(String number);

  /**
   * Find id by number long.
   *
   * @param numberData the number data
   * @return the long
   */
  long findIdByNumber(long numberData);

  /**
   * Create number numbeer.
   *
   * @param numberData the number data
   * @return the numbeer
   */
  Numbeer createNumber(long numberData);

  /**
   * Find number numbeer.
   *
   * @param numberData the number data
   * @return the numbeer
   */
  Numbeer findNumber(long numberData);

  /**
   * Update number.
   *
   * @param id the id
   * @param numberData the number data
   */
  void updateNumber(long id, long numberData);

  /**
   * Delete number.
   *
   * @param id the id
   */
  void deleteNumber(long id);
}
