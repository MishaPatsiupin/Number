package com.example.demo.service;

import com.example.demo.controller.FactInfoController;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/** The type Counter service. */
@Data
@Service
public class CounterService {

  private static CounterService instance;

  private AtomicInteger requestCount;
  private static final Logger logger = LoggerFactory.getLogger(CounterService.class);

  private CounterService() {
    requestCount = new AtomicInteger(0);
    logger.info("CounterService initialized");
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static CounterService getInstance() {
    if (instance == null) {
      instance = new CounterService();
    }
    return instance;
  }

  /** Increment request count. */
  public synchronized void incrementRequestCount() {
    requestCount.incrementAndGet();
    logger.info("Request count incremented to " + requestCount.get());
  }

  /**
   * Gets request count.
   *
   * @return the request count
   */
  public int getRequestCount() {
    return requestCount.get();
  }
}
