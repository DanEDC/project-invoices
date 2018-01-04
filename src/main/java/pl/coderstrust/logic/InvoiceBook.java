package pl.coderstrust.logic;

import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
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
    invoices.forEach(this::saveInvoice);
  }

  public Invoice getInvoice(Integer invoiceId) {
    return database.getInvoice(invoiceId);
  }

  public List<Invoice> getAllInvoices() {
    return database.getAllInvoices();
  }

  public List<Invoice> getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
    List<Invoice> list = new ArrayList<>();
    orderedInvoicesId.forEach(id -> list.add(this.getInvoice(id)));
    return list;
  }

  public boolean removeInvoice(Integer invoiceId) {
    return database.removeInvoice(invoiceId);
  }

  public boolean[] removeInvoicesById(Collection<Integer> toBeRemovedInvoicesId) {
    boolean[] results = new boolean[toBeRemovedInvoicesId.size()];
    int i = 0;
    for (Integer integer : toBeRemovedInvoicesId) {
      results[i] = this.removeInvoice(integer);
      i++;
    }
    return results;
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
