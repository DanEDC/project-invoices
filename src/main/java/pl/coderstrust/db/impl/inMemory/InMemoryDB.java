package pl.coderstrust.db.impl.inMemory;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;
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
  public boolean saveInvoices(Collection<Invoice> invoices) {
    do{
      /**
       * //ToDo nie nadaje numerow fakturon dodanym z kolekcji
       * */
    } while ()
    return this.invoices.addAll(invoices);
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
  public List<Invoice> getInvoicesList() {
    return invoices;
  }
  
  
  @Override
  public String toString() {
    return "InMemoryDB\tinvoices:\n"
        + invoices;
  }
  
  /**
   * Awful methods here
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof InMemoryDB)) {
      return false;
    }
    
    InMemoryDB that = (InMemoryDB) object;
    
    if (hardOperationsCounter != that.hardOperationsCounter) {
      return false;
    }
    if (invoices != null ? !invoices.equals(that.invoices) : that.invoices != null) {
      return false;
    }
    return invoiceId != null ? invoiceId.equals(that.invoiceId) : that.invoiceId == null;
  }
  
  @Override
  public int hashCode() {
    int result = hardOperationsCounter;
    result = 31 * result + (invoices != null ? invoices.hashCode() : 0);
    result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
    return result;
  }
}
