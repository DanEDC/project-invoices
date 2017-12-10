package pl.coderstrust.db.impl.memory;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InMemory implements Database {
  
  
  private List<Invoice> inMemory = new ArrayList<>();
  private final AtomicReference<Integer> invoiceId = new AtomicReference<>(0);
  
  public InMemory(List<Invoice> inMemory) {
    this.inMemory = inMemory;
  }
  
  @Override
  public final Integer getNextInvoiceId() {
    invoiceId.getAndSet(invoiceId.get() + 1);
    return invoiceId.get();
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
    return invoiceId.get() != null ? invoiceId.get().equals(that.invoiceId.get()) : that.invoiceId
        .get() == null;
  }
  
  @Override
  public int hashCode() {
    int result = inMemory != null ? inMemory.hashCode() : 0;
    result = 31 * result + (invoiceId.get() != null ? invoiceId.get().hashCode() : 0);
    return result;
  }
}
