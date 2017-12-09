package pl.coderstrust.db.impl.inMemory;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDB implements Database {
  
  private int hardOperationsCounter = 0;
  private List<Invoice> invoices = new ArrayList<>();
  private Integer invoiceId = 0;
  
  @Override
  public Integer getNextInvoiceId() {
    invoiceId++;
    return invoiceId;
  }
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    invoice.setInvoiceId(getNextInvoiceId());
    invoices.add(invoice);
    hardOperationsCounter++;
    return false;
  }
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    Invoice candidate;
    do {
      candidate = invoices.iterator().next();
    } while (!invoiceId.equals(candidate.getInvoiceId()));
    return candidate;
  }
  
  @Override
  public boolean removeInvoice(Integer invoiceId) {
    hardOperationsCounter++;
    return invoices.remove(getInvoice(invoiceId));
  }
  
  
  @Override
  public List<Invoice> listInvoices() {
    return invoices;
  }
}
