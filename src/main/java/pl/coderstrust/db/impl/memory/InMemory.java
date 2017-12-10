package pl.coderstrust.db.impl.memory;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InMemory implements Database {
  
  private List<Invoice> inMemory = new ArrayList<>();
  private Integer invoiceId = 0;
  
  @Override
  public final Integer getNextInvoiceId() {
    invoiceId++;
    return invoiceId;
  }
  
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    return inMemory.add(invoice);
  }
  
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    Invoice invoice = null;
    for (Invoice candidate : inMemory) {
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoice = candidate;
        break;
      }
    }
    return invoice;
  }
  
  @Override
  public List<Invoice> getAllInvoices() {
    return inMemory;
  }
  
  
  @Override
  public boolean removeInvoice(Integer invoiceId) {
    return inMemory.remove(getInvoice(invoiceId));
  }
  
  
  @Override
  public String toString() {
    return "InMemory\tinMemory:\n"
        + inMemory;
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof InMemory)) {
      return false;
    }
    
    InMemory that = (InMemory) object;
  
    if (inMemory != null ? !inMemory.equals(that.inMemory)
        : that.inMemory != null) {
      return false;
    }
    return invoiceId != null ? invoiceId.equals(that.invoiceId) : that.invoiceId == null;
  }
  
  @Override
  public int hashCode() {
    int result = inMemory != null ? inMemory.hashCode() : 0;
    result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
    return result;
  }
}
