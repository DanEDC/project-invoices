package pl.coderstrust.logic;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class InvoiceBook {
  
  private Database invoiceBook;
  
  public InvoiceBook(Database database) {
    this.invoiceBook = database;
  }
  
  public void saveInvoice(Invoice invoice) {
    System.out.println(invoiceBook.saveInvoice(invoice));
  }
  
  public void saveInvoices(Collection<Invoice> invoices) {
    System.out.println(this.invoiceBook.saveInvoices(invoices));
  }
  
  public Invoice getInvoice(Integer invoiceId) {
    return invoiceBook.getInvoice(invoiceId);
  }
  
  public List<Invoice> getInvoices(Collection<Integer> orderedInvoicesId) {
    return invoiceBook.getInvoices(orderedInvoicesId);
  }
  
  public void removeInvoice(Integer invoiceId) {
    System.out.println(invoiceBook.removeInvoice(invoiceId));
  }
  
  public void removeInvoices(Collection<Integer> toBeRemovedInvoicesId) {
    System.out.println(Arrays.toString(
        invoiceBook.removeInvoices(toBeRemovedInvoicesId)));
    /*condition if not found should be here*/
  }
  
  public Database getInvoiceBook() {
    return invoiceBook;
  }
  
  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + invoiceBook;
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
    
    return invoiceBook != null ? invoiceBook.equals(that.invoiceBook) : that.invoiceBook == null;
  }
  
  @Override
  public int hashCode() {
    return invoiceBook != null ? invoiceBook.hashCode() : 0;
  }
}
