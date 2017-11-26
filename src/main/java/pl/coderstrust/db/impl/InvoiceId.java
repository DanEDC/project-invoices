package pl.coderstrust.db.impl;

public class InvoiceId {
  
  private static int id = 0;
  
  public InvoiceId() {
    id = id++;
  }
}
