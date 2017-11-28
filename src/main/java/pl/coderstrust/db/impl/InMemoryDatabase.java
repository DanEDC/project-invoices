package pl.coderstrust.db.impl;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase implements Database {
  
  private List<Invoice> invoices;
  private int invoiceCounter;
  
  public InMemoryDatabase() {
    this.invoices = new ArrayList<>();
    this.invoiceCounter = 0;
  }
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    invoiceCounter++;
    return invoices.add(invoice);
    
  }
  
  @Override
  public boolean removeInvoice(Invoice invoice) {
    return invoices.remove(invoice);
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof InMemoryDatabase)) {
      return false;
    }
    
    InMemoryDatabase that = (InMemoryDatabase) object;
    
    if (invoiceCounter != that.invoiceCounter) {
      return false;
    }
    return invoices != null ? invoices.equals(that.invoices) : that.invoices == null;
  }
  
  @Override
  public int hashCode() {
    int result = invoices != null ? invoices.hashCode() : 0;
    result = 31 * result + invoiceCounter;
    return result;
  }
  
  public List<Invoice> getInvoices() {
    return invoices;
  }
  
  public void setInvoices(List<Invoice> invoices) {
    this.invoices = invoices;
  }
  
  public int getInvoiceCounter() {
    return invoiceCounter;
  }
  
  public void setInvoiceCounter(int invoiceCounter) {
    this.invoiceCounter = invoiceCounter;
  }
  
  @Override
  public String toString() {
    return "\n"
        + this.getClass().getSimpleName()
        + invoices;
  }
}
