package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.util.Collection;
import java.util.List;

public interface Database {
  
  Integer getNextInvoiceId();
  
  boolean saveInvoice(Invoice invoice);
  
  boolean saveInvoices(Collection<Invoice> invoices);
  
  Invoice getInvoice(Integer invoiceId);
  
  List<Invoice> getInvoices(Collection<Integer> orderedInvoicesId);
  
  boolean removeInvoice(Integer invoiceId);
  
  boolean[] removeInvoices(Collection<Integer> toBeRemovedInvoicesId);
  
  
}
