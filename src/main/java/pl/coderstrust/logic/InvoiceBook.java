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
      logger.info("Invoice saved :" + id);
      return id;
    } else {
      logger.info("Failed to save Invoice " + id);
      return 0;
    }
  }
  
  public List<Integer> saveInvoices(Collection<Invoice> invoices) {
    logger.debug("saveInvoices called");
    List<Integer> savedInvoicesIdList = new ArrayList<>();
    List<Invoice> notSavedInvoicesLsit = new ArrayList<>();
  
    Integer currentId;
    for (Invoice invoice : invoices) {
      currentId = this.saveInvoice(invoice);
      if (currentId == 0) {
        notSavedInvoicesLsit.add(invoice);
      } else {
        savedInvoicesIdList.add(currentId);
      }
    }
  
    if (notSavedInvoicesLsit.isEmpty()) {
      logger.info("All invoices saved with id: " + savedInvoicesIdList);
    } else {
      logger.error("Failed to save following Invoices: " + notSavedInvoicesLsit);
    }
    return savedInvoicesIdList;
  }
  
  public Invoice getInvoiceById(Integer invoiceId) {
    logger.debug("getInvoiceById called");
    Invoice invoice = database.getInvoiceById(invoiceId);
    if (invoice != null) {
      logger.info("Invoice returned: " + invoiceId);
      return invoice;
    } else {
      logger.warn("Invoice not found: " + invoiceId);
      return null;
    }
  }
  
  public List<Invoice> getAllInvoices() {
    logger.debug("getAllInvoices called");
    List<Invoice> invoices;
    invoices = database.getAllInvoices();
    if (invoices.isEmpty()) {
      logger.warn("Returned empty list of invoices");
    } else {
      logger.info("Returned all invoices");
    }
    return invoices;
  }

  public List<Invoice> getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
    logger.debug("getListOfInvoiceById called");
    List<Invoice> foundInvoicesList = new ArrayList<>();
    List<Integer> notFoundInvoices = new ArrayList<>();
    Invoice currentInvoice;
  
    for (Integer id : orderedInvoicesId) {
      currentInvoice = this.getInvoiceById(id);
      if (currentInvoice == null) {
        notFoundInvoices.add(id);
      }
      foundInvoicesList.add(currentInvoice);
    }
  
    if (notFoundInvoices.isEmpty()) {
      logger.debug("All invoices found");
    } else {
      logger.warn("Failed to find following Invoices " + notFoundInvoices);
    }
  
    return foundInvoicesList;
  }
  
  public Invoice removeInvoiceById(Integer invoiceId) {
    logger.debug("removeInvoiceById called");
    Invoice invoice = database.removeInvoiceById(invoiceId);
    if (invoice != null) {
      logger.info("Invoice " + invoiceId + " returned");
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
    logger.debug("getInvoicesFromDateToDate called");
    return database.getInvoicesFromDateToDate(since, to);
  }

  public int getYesterdayInvoicesNo(LocalDate yesterday) {
    logger.debug("getYesterdayInvoicesNo called");
    return getInvoicesFromDateToDate(yesterday, yesterday).size();
  }
  
  
}
