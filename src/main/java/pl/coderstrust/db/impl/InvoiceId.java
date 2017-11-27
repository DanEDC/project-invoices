package pl.coderstrust.db.impl;

import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceId {
  
  /**
   * Mateusz Dzielinski code copied. Source : https://stackoverflow.com/questions/25852227/implementing-class-with-unique-identification-number
   */
  private static final AtomicInteger nextId = new AtomicInteger();
  
  private final int id = nextId.getAndIncrement();
  
  public InvoiceId() {
  }
  
  @Override
  public String toString() {
    return "*" + id;
  }
}