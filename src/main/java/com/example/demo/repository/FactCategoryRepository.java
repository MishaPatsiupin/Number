package com.example.demo.repository;

import com.example.demo.entity.FactCategoryEntity;
import com.example.demo.entity.FactEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface FactCategoryRepository extends CrudRepository<FactCategoryEntity, Long> {
    @Query("SELECT fc FROM FactCategoryEntity fc WHERE fc.fact.number.id = :factId")
    List<FactCategoryEntity> findFactCategoriesByFactId(@Param("factId") long factId);

    @Query("SELECT fc FROM FactCategoryEntity fc WHERE fc.fact.id = :idFact")
    FactCategoryEntity findFactCategoryEntitiesById(long idFact);
    @Query("SELECT fc FROM FactCategoryEntity fc " +
            "JOIN fc.fact f " +
            "JOIN f.number n " +
            "WHERE n.numberData = :numberData")
    List<FactCategoryEntity> findByNumberData(@Param("numberData") long numberData);


    @Query("SELECT fc FROM FactCategoryEntity fc WHERE fc.fact = :factEntity")
    FactCategoryEntity getFactCategoryByFactEntity(@Param("factEntity") FactEntity factEntity);



}
