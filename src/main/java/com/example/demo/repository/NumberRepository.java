package com.example.demo.repository;

import com.example.demo.entity.NumberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends CrudRepository<NumberEntity, Long> {

   NumberEntity findByNumberData(long numberData);
}
