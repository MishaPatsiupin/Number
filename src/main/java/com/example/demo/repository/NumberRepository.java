package com.example.demo.repository;

import com.example.demo.entity.Number;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends CrudRepository<Number, Long> {

   Number findByNumberData(long numberData);
}
