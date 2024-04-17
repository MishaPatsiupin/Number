package com.example.demo.repository;

import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** The interface Fact repository. */
public interface FactRepository extends CrudRepository<Fact, Long> {

  /**
   * Find by description fact.
   *
   * @param description the description
   * @return the fact
   */
  @Query("SELECT f FROM Fact f WHERE f.description = :description")
  Fact findByDescription(@Param("description") String description);

  /**
   * Find fact entity by number list.
   *
   * @param numId the num id
   * @return the list
   */
  @Query("SELECT f FROM Fact f WHERE f.number = :number")
  List<Fact> findFactEntityByNumber(@Param("number") Numbeer numId);

  /**
   * Count by number id long.
   *
   * @param numberId the number id
   * @return the long
   */
  @Query("SELECT COUNT(f) FROM Fact f WHERE f.number.id = :numberId")
  long countByNumberId(long numberId);

  /**
   * Find by number fact.
   *
   * @param number the number
   * @return the fact
   */
  Fact findByNumber(Numbeer number);
}
