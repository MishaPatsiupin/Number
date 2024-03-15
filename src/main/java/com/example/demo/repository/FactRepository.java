package com.example.demo.repository;

import com.example.demo.entity.FactEntity;
import org.springframework.data.repository.CrudRepository;

public interface FactRepository extends CrudRepository<FactEntity, Long> {
}
