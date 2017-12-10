package pl.coderstrust.logic;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceBook {
  
  private Database database;
  
  public InvoiceBook(Database database) {
    this.database = database;
  }
  
  public void saveInvoice(Invoice invoice) {
    invoice.setInvoiceId(database.getNextInvoiceId());
    database.saveInvoice(invoice);
  }
  
  public void saveInvoices(Collection<Invoice> invoices) {
    for (Invoice invoice : invoices) {
      saveInvoice(invoice);
    }
  }
  
  public Invoice getInvoice(Integer invoiceId) {
    return database.getInvoice(invoiceId);
  }
  
  public List<Invoice> getAllInvoices() {
    return database.getAllInvoices();
  }
  
  public List<Invoice> getInvoices(Collection<Integer> orderedInvoicesId) {
    List<Invoice> list = new ArrayList<>();
    orderedInvoicesId.forEach(id -> list.add(database.getInvoice(id)));
    return list;
  }
  
  public void removeInvoice(Integer invoiceId) {
    database.removeInvoice(invoiceId);
  }
  
  public void removeInvoices(Collection<Integer> toBeRemovedInvoicesId) {
    toBeRemovedInvoicesId.forEach(id -> database.removeInvoice(id));
  }
  
  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + database;
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof InvoiceBook)) {
      return false;
    }
    
    InvoiceBook that = (InvoiceBook) object;
  
    return database != null ? database.equals(that.database) : that.database == null;
  }
  
  @Override
  public int hashCode() {
    return database != null ? database.hashCode() : 0;
  }
}
