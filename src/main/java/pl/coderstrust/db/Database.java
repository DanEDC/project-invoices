package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.util.Collection;
import java.util.List;

public interface Database {
  
  Integer getNextInvoiceId();
  
  boolean saveInvoice(Invoice invoice);
  
  boolean saveInvoices(Collection<Invoice> invoices);
  
  Invoice getInvoice(Integer invoiceId);
  
  List<Invoice> getInvoicesList();
  
  boolean removeInvoice(Integer invoiceId);
  
  
}
