package com.example.demo.repository;

import com.example.demo.entity.NumberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends CrudRepository<NumberEntity, Long> {

   @Query("SELECT n FROM NumberEntity n WHERE n.numberData = :numberData")
   NumberEntity findByNumberData(@Param("numberData") long numberData);
}
