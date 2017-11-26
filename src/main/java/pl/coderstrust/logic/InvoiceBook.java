package pl.coderstrust.logic;

import pl.coderstrust.db.impl.InMemoryDatabase;
import pl.coderstrust.model.Invoice;

public class InvoiceBook {
  
  private InMemoryDatabase invoices;
  
  public InvoiceBook() {
    this.invoices = new InMemoryDatabase();
  }
  
  public void addInvoice(Invoice invoice) {
    invoices.saveInvoice(invoice);
  }
  
  public void removeInvoice(Invoice invoice) {
    invoices.removeInvoice(invoice);
  }
  
  @Override
  public String toString() {
    return "InvoiceBook/"
        + invoices;
  }
}
