package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.util.List;

public interface Database {
  
  Integer getNextInvoiceId();
  
  boolean saveInvoice(Invoice invoice);
  
  Invoice getInvoice(Integer invoiceId);
  
  List<Invoice> getAllInvoices();
  
  boolean removeInvoice(Integer invoiceId);
  
  
  
}
