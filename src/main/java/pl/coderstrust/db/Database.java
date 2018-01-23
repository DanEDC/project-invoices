package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.util.List;

public interface Database {

  Integer getNextInvoiceId();

  boolean saveInvoice(Invoice invoice);

  Invoice getInvoiceById(Integer invoiceId);

  List<Invoice> getAllInvoices();
  
  Invoice removeInvoiceById(Integer invoiceId);
  
  List<Invoice> removeAllInvoices();
  
  List<Integer> getAllIds();
  
  boolean dropDatabase();
  
}
