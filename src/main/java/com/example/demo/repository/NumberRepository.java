package com.example.demo.repository;

import com.example.demo.entity.Numbeer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends CrudRepository<Numbeer, Long> {

   @Query("SELECT n FROM Numbeer n WHERE n.numberData = :numberData")
   Numbeer findByNumberData(@Param("numberData") long numberData);
}
