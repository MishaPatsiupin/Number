package com.example.demo.repository;

import com.example.demo.entity.Numbeer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** The interface Number repository. */
@Repository
public interface NumberRepository extends CrudRepository<Numbeer, Long> {

  /**
   * Find by number data numbeer.
   *
   * @param numberData the number data
   * @return the numbeer
   */
  @Query("SELECT n FROM Numbeer n WHERE n.numberData = :numberData")
  Numbeer findByNumberData(@Param("numberData") long numberData);
}
