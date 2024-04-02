package com.example.demo.repository;

import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FactRepository extends CrudRepository<Fact, Long> {

    @Query("SELECT f FROM Fact f WHERE f.description = :description")
    Fact findByDescription(@Param("description") String description);

    @Query( "SELECT f FROM Fact f WHERE f.number = :number")
    List<Fact> findFactEntityByNumber(@Param("number") Numbeer numId);

    @Query("SELECT COUNT(f) FROM Fact f WHERE f.number.id = :numberId")
    long countByNumberId(long numberId);

    Fact findByNumber(Numbeer number);
}
