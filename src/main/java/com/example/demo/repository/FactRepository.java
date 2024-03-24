package com.example.demo.repository;

import com.example.demo.entity.FactEntity;
import com.example.demo.entity.NumberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FactRepository extends CrudRepository<FactEntity, Long> {

    long findIdByNumberId(long idNum);
    @Query("SELECT f FROM FactEntity f WHERE f.description = :description")
    FactEntity findByDescription(@Param("description") String description);

    @Query( "SELECT f FROM FactEntity f WHERE f.number = :number")
    List<FactEntity> findFactEntityByNumber(@Param("number") NumberEntity numId);

    @Query("SELECT COUNT(f) FROM FactEntity f WHERE f.number.id = :numberId")
    long countByNumberId(long numberId);

    FactEntity findByNumber( NumberEntity number);
}
