package pl.coderstrust.logic;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

public class InvoiceBook {
  
  private Database invoices;
  
  public InvoiceBook(Database db) {
    this.invoices = db;
  }
  
  public void addInvoice(Invoice invoice) {
    invoices.saveInvoice(invoice);
  }
  
  public void removeInvoice(Integer invoiceId) {
    invoices.removeInvoice(invoiceId);
  }
  
  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + invoices;
  }
  
  
}
