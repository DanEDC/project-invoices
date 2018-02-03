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
    Invoice invoice = database.getInvoiceById(invoiceId);
    if (invoice == null) {
      logger.info("Invoice not found: " + invoiceId);
    } else {
      logger.info("Invoice returned: " + invoiceId);
    }
    return invoice;
  }
  
  public List<Invoice> getAllInvoices() {
    List<Invoice> invoices;
    invoices = database.getAllInvoices();
    logger.info("Returned all invoices in number: " + invoices.size());
    return invoices;
  }

  public List<Invoice> getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
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
      logger.info("Returned ordered invoices in number: " + foundInvoicesList.size());
    } else {
      logger.warn("Failed to find following Invoices " + notFoundInvoices);
    }
    return foundInvoicesList;
  }
  
  public Invoice removeInvoiceById(Integer invoiceId) {
    Invoice invoice = database.removeInvoiceById(invoiceId);
    if (invoice != null) {
      logger.info("Deleted and returned Invoice: " + invoiceId);
      return invoice;
    } else {
      logger.info("Failed to delete Invoice " + invoiceId);
      return null;
    }
  }

  public List<Invoice> removeAllInvoices() {
    List<Invoice> removedInvoices = database.removeAllInvoices();
    logger.info("Removed and returned all Invoices in a number: " + removedInvoices.size());
    return removedInvoices;
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
    List<Invoice> invoicesFound = database.getInvoicesFromDateToDate(since, to);
    logger.info("Returned " + invoicesFound.size() + " invoices from period: "
        + since + " to " + to);
    return database.getInvoicesFromDateToDate(since, to);
  }

  public int getYesterdayInvoicesNo(LocalDate yesterday) {
    return getInvoicesFromDateToDate(yesterday, yesterday).size();
  }
  
  
}
