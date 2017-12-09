package pl.coderstrust.db.impl.inMemory;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDB implements Database {
  
  int operationsCounver = 0;
  private Map<Integer, Invoice> invoices = new HashMap<>();
  private Integer invoiceId = 0;
  
  @Override
  public Integer nextInvoiceId() {
    invoiceId++;
    return invoiceId;
  }
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    invoice.setInvoiceId(nextInvoiceId());
    invoices.put(invoice.getInvoiceId(),invoice);
    return false;
  }
  
  @Override
  public Invoice removeInvoice(Integer invoiceId) {
        return invoices.remove(invoiceId);
  }
  
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    return null;
  }
  
  @Override
  public List<Invoice> listInvoices(Database db) {
    return null;
  }
}
