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
    logger.info("InvoiceBook initiated");
  }
  
  public Integer saveInvoice(Invoice invoice) {
    logger.debug("saveInvoice called");
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
    logger.debug("saveInvoices called");
    List<Integer> ids = new ArrayList<>();
    List<Invoice> notSavedInvoices = new ArrayList<>();
  
    Integer currentId;
    for (Invoice invoice : invoices) {
      currentId = this.saveInvoice(invoice);
      if (currentId == 0) {
        notSavedInvoices.add(invoice);
      } else {
        ids.add(currentId);
      }
    }
  
    if (notSavedInvoices.isEmpty()) {
      logger.info("All invoices saved with id: " + ids);
    } else {
      logger.error("Following Invoices failed to save: " + notSavedInvoices);
    }
    return ids;
  }
  
  public Invoice getInvoiceById(Integer invoiceId) {
    logger.debug("getInvoiceById called");
    Invoice invoice = database.getInvoiceById(invoiceId);
    if (invoice != null) {
      logger.info("Invoice " + invoiceId + " found");
      return invoice;
    } else {
      logger.warn("Invoice " + invoiceId + " not found");
      return null;
    }
  }
  
  public List<Invoice> getAllInvoices() {
    logger.debug("getAllInvoices called");
    List<Invoice> invoices;
    invoices = database.getAllInvoices();
    if (invoices.isEmpty()) {
      logger.info("All invoices returned");
    } else {
      logger.warn("Returned empty list of invoices");
    }
    return invoices;
  }

  public List<Invoice> getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
    logger.debug("getListOfInvoiceById called");
    List<Invoice> list = new ArrayList<>();
    orderedInvoicesId.forEach(id -> list.add(this.getInvoiceById(id)));
    return list;
  }
  
  public Invoice removeInvoiceById(Integer invoiceId) {
    logger.debug("removeInvoiceById called");
    Invoice invoice = database.removeInvoiceById(invoiceId);
    if (invoice != null) {
      logger.info("Invoice " + invoiceId + " deleted");
      return invoice;
    } else {
      logger.info("Invoice " + invoiceId + " failed to delete");
      return null;
    }
  }

  public List<Invoice> removeAllInvoices() {
    logger.debug("removeAllInvoices called");
    return database.removeAllInvoices();
  }
  
  public List<Invoice> removeInvoicesById(Collection<Integer> toBeRemovedInvoicesId) {
    logger.debug("removeInvoicesById called");
    List<Invoice> results = new ArrayList<>();
    for (Integer integer : toBeRemovedInvoicesId) {
      results.add(this.removeInvoiceById(integer));
    }
    return results;
  }
  
  @Override
  public String toString() {
    logger.debug("toString called");
    return this.getClass().getSimpleName()
        + database;
  }

  @Override
  public boolean equals(Object object) {
    logger.debug("equals called");
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
    logger.debug("hashCode called");
    return database != null ? database.hashCode() : 0;
  }
  
  public List<Invoice> getInvoicesFromDateToDate(LocalDate since, LocalDate to) {
    return database.getInvoicesFromDateToDate(since, to);
  }
  
  
}
