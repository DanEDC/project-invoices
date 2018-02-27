package pl.coderstrust.db;

import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.List;


public interface Database {

  Integer getNextInvoiceId();

  boolean saveInvoice(Invoice invoice);

  Invoice getInvoiceById(Integer invoiceId);
  
  List<Invoice> getInvoicesFromDateToDate(LocalDate from, LocalDate to);

  List<Invoice> getAllInvoices();
  
  Invoice removeInvoiceById(Integer invoiceId);
  
  List<Invoice> removeAllInvoices();
  
  List<Integer> getAllIds();
  
  boolean dropDatabase();
  
}
