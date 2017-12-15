package pl.coderstrust.db.impl.memory;

import org.springframework.stereotype.Repository;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemory implements Database {

  private List<Invoice> database = new ArrayList<>();
  private Integer invoiceId = 0;

  @Override
  public final Integer getNextInvoiceId() {
    return invoiceId++;
  }


  @Override
  public boolean saveInvoice(Invoice invoice) {
    return database.add(invoice);
  }


  @Override
  public Invoice getInvoice(Integer invoiceId) {
    Invoice invoice = null;
    for (Invoice candidate : database) {
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoice = candidate;
        break;
      }
    }
    return invoice;
  }

  @Override
  public List<Invoice> getAllInvoices() {
    return database;
  }


  @Override
  public boolean removeInvoice(Integer invoiceId) {
    return database.remove(getInvoice(invoiceId));
  }


  @Override
  public String toString() {
    return "InMemory\tdatabase:\n"
        + database;
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

    if (database != null ? !database.equals(that.database)
        : that.database != null) {
      return false;
    }
    return invoiceId != null ? invoiceId.equals(that.invoiceId) : that.invoiceId == null;
  }

  @Override
  public int hashCode() {
    int result = database != null ? database.hashCode() : 0;
    result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
    return result;
  }
}
