package pl.coderstrust.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InvoiceBook {
  
  private static Logger logger = LoggerFactory.getLogger(InvoiceBook.class);
  
  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }
  
  public Integer saveInvoice(Invoice invoice) {
    Integer id = database.getNextInvoiceId();
    invoice.setInvoiceId(id);
    if (database.saveInvoice(invoice)) {
      logger.info("Invoice " + id + "saved");
      return id;
    } else {
      logger.info("Failed to save Invoice " + id);
      return 0;
    }
  }
  
  public List<Integer> saveInvoices(Collection<Invoice> invoices) {
    List<Integer> ids = new ArrayList<>();
    for (Invoice invoice : invoices) {
      ids.add(saveInvoice(invoice));
    }
    return ids;
  }
  
  public Invoice getInvoiceById(Integer invoiceId) {
    return database.getInvoiceById(invoiceId);
  }
  
  public List<Invoice> getAllInvoices() {
    return database.getAllInvoices();
  }

  public List<Invoice> getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
    List<Invoice> list = new ArrayList<>();
    orderedInvoicesId.forEach(id -> list.add(this.getInvoiceById(id)));
    return list;
  }
  
  public Invoice removeInvoice(Integer invoiceId) {
    return database.removeInvoiceById(invoiceId);
  }

  public List<Invoice> removeAllInvoices() {
    return database.removeAllInvoices();
  }
  
  public List<Invoice> removeInvoicesById(Collection<Integer> toBeRemovedInvoicesId) {
    List<Invoice> results = new ArrayList<>();
    for (Integer integer : toBeRemovedInvoicesId) {
      results.add(this.removeInvoice(integer));
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
  
  public List<Invoice> getInvoicesFromDateToDate(LocalDate since, LocalDate to) {
    return database.getInvoicesFromDateToDate(since, to);
  }
  
  
}
