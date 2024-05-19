package com.example.demo.aspect;

import com.example.demo.service.CounterService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/** The type Counter aspect. */
@Aspect
@Component
public class CounterAspect {

  private final CounterService counterService;

  /**
   * Instantiates a new Counter aspect.
   *
   * @param counterService the counter service
   */
  public CounterAspect(CounterService counterService) {
    this.counterService = counterService;
  }

  /** Controller methods. */
  @Pointcut("execution(* com.example.demo.controller.FactInfoController.*(..))")
  public void controllerMethods() {}

  /** After controller method. */
  @AfterReturning("controllerMethods()")
  public void afterControllerMethod() {
    counterService.incrementRequestCount();
  }
}
