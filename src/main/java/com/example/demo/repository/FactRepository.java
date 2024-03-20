package com.example.demo.repository;

import com.example.demo.entity.FactEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface FactRepository extends CrudRepository<FactEntity, Long> {

    long findIdByNumberId(long idNum);
    @Query("SELECT f FROM FactEntity f WHERE f.description = :description")
    FactEntity findByDescription(@Param("description") String description);
}
