package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.util.List;

public interface Database {
  
  Integer nextInvoiceId();
  
  boolean saveInvoice(Invoice invoice);
  
  Invoice removeInvoice(Integer invoiceId);
  
  Invoice getInvoice(Integer invoiceId);
  
  List<Invoice> listInvoices(Database db);
  
  
}
