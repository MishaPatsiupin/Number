package com.example.demo.service.defaultt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.entity.Fact;
import com.example.demo.entity.Numbeer;
import com.example.demo.repository.FactRepository;
import com.example.demo.repository.NumberRepository;
import com.example.demo.service.FactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** The type Default fact service. */
@Service
@RequiredArgsConstructor
public class DefaultFactService implements FactService {
  private final Logger logger = LoggerFactory.getLogger(DefaultFactService.class);

  private final FactRepository factRepository;
  private final NumberRepository numberRepository;

  @Override
  public Fact createFact(long number, String description) {
    Numbeer existingNumber = numberRepository.findByNumberData(number);

    if (existingNumber == null) {
      existingNumber = new Numbeer();
      existingNumber.setNumberData(number);
      existingNumber =
          numberRepository.save(existingNumber); // Save the new Numbeer to generate an ID
      logger.info("Created new Numbeer entity.");
    } else {
      logger.info("Found existing Numbeer entity.");
    }

    Fact factEntity = new Fact();
    factEntity.setNumber(existingNumber);
    factEntity.setDescription(description);
    factRepository.save(factEntity);
    logger.info("Created new Fact entity.");
    return factEntity;
  }

  @Override
  public Fact findFact(String description) {
    Fact factEntity = factRepository.findByDescription(description);

    if (factEntity != null) {
      logger.info("Fact found.");
    } else {
      logger.info("Fact not found.");
    }

    return factEntity;
  }

  @Override
  public Fact getFactByNumberId(long number) {
    Numbeer numberEntity = numberRepository.findByNumberData(number);
    Fact factEntity = factRepository.findByNumber(numberEntity);

    if (factEntity != null) {
      logger.info("Fact found.}");
    } else {
      logger.info("Fact not found.");
    }

    return factEntity;
  }
}
