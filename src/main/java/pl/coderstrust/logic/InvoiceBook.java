package pl.coderstrust.logic;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.Item;

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
    {
      Integer itemId = 1;
      for (Item item : invoice.getItems()) {
        item.setItemId(itemId);
        itemId++;
      }
    }
    database.saveInvoice(invoice);
  }
  
  public void saveInvoices(Collection<Invoice> invoices) {
    invoices.forEach(this::saveInvoice);
  }
  
  public Invoice getInvoice(Integer invoiceId) {
    return database.getInvoice(invoiceId);
  }
  
  public List<Invoice> getAllInvoices() {
    return database.getAllInvoices();
  }
  
  public List<Invoice> getInvoices(Collection<Integer> orderedInvoicesId) {
    List<Invoice> list = new ArrayList<>();
    orderedInvoicesId.forEach(id -> list.add(this.getInvoice(id)));
    return list;
  }
  
  public void removeInvoice(Integer invoiceId) {
    database.removeInvoice(invoiceId);
  }
  
  public void removeInvoices(Collection<Integer> toBeRemovedInvoicesId) {
    toBeRemovedInvoicesId.forEach(this::removeInvoice);
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
  
    return database != null ? database.equals(
        that.database) : that.database == null;
  }
  
  @Override
  public int hashCode() {
    return database != null ? database.hashCode() : 0;
  }
}
