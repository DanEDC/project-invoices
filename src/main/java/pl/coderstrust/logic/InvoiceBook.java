package pl.coderstrust.logic;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.Collection;

public class InvoiceBook {
  
  private Database invoices;
  
  public InvoiceBook(Database db) {
    this.invoices = db;
  }
  
  public void saveInvoice(Invoice invoice) {
    System.out.println(invoices.saveInvoice(invoice));
  }
  
  public void saveInvoices(Collection<Invoice> invoices) {
    System.out.println(this.invoices.saveInvoices(invoices));
  }
  
  public void removeInvoice(Integer invoiceId) {
    System.out.println(invoices.removeInvoice(invoiceId));
  }
  
  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + invoices;
  }
  /*
  *   Integer getNextInvoiceId();
  
  boolean saveInvoice(Invoice invoice);
  
  boolean saveInvoices(Collection<Invoice> invoices);
  
  Invoice getInvoice(Integer invoiceId);
  
  List<Invoice> getInvoicesList();
  
  boolean removeInvoice(Integer invoiceId);*/
  
}
