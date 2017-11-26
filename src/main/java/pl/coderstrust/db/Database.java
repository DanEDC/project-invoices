package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

public interface Database {
  
  /**
   * In memory - to be implemented saving into file.
   */
  
  boolean saveInvoice(Invoice invoice);
  
  boolean removeInvoice(Invoice invoice);
  
  
}
