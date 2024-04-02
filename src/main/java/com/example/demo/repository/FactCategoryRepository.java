package com.example.demo.repository;

import com.example.demo.entity.Fact;
import com.example.demo.entity.FactCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface FactCategoryRepository extends CrudRepository<FactCategory, Long> {
    @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.number.id = :factId")
    List<FactCategory> findFactCategoriesByFactId(@Param("factId") long factId);

    @Query("SELECT fc FROM FactCategory fc WHERE fc.fact.id = :idFact")
    FactCategory findFactCategoryEntitiesById(long idFact);
    @Query("SELECT fc FROM FactCategory fc " +
            "JOIN fc.fact f " +
            "JOIN f.number n " +
            "WHERE n.numberData = :numberData")
    List<FactCategory> findByNumberData(@Param("numberData") long numberData);


    @Query("SELECT fc FROM FactCategory fc WHERE fc.fact = :factEntity")
    FactCategory getFactCategoryByFactEntity(@Param("factEntity") Fact factEntity);



}
