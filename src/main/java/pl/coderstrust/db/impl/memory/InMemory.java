package pl.coderstrust.db.impl.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@ConditionalOnProperty(name = "pl.coderstrust.db.impl.DatabaseImpl", havingValue = "inMemory")
public class InMemory implements Database {
  
  private static Logger logger = LoggerFactory.getLogger(InMemory.class);
  
  private List<Invoice> database = new ArrayList<>();
  private Integer invoiceId = 0;
  
  public InMemory() {
    logger.info("InFile Database initiated");
  }
  
  @Override
  public final Integer getNextInvoiceId() {
    logger.debug("getNextInvoiceId called");
    return ++invoiceId;
  }

  @Override
  public boolean saveInvoice(Invoice invoice) {
    logger.debug("saveInvoice called");
    return database.add(invoice);
  }

  @Override
  public Invoice getInvoiceById(Integer invoiceId) {
    logger.debug("getInvoiceById called");
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
  public List<Invoice> getInvoicesFromDateToDate(LocalDate from, LocalDate to) {
    logger.debug("getInvoicesFromDateToDate called");
    List<Invoice> invoices = new ArrayList<>();
    for (Invoice invoice : database) {
      if (invoice.compareTo(from) >= 0 && invoice.compareTo(to) <= 0) {
        invoices.add(invoice);
      }
    }
    return invoices;
  }
  
  @Override
  public List<Invoice> getAllInvoices() {
    logger.debug("getAllInvoices called");
    return database;
  }

  @Override
  public Invoice removeInvoiceById(Integer invoiceId) {
    logger.debug("removeInvoiceById called");
    int index = database.indexOf(this.getInvoiceById(invoiceId));
    return database.remove(index);
  }
  
  @Override
  public List<Invoice> removeAllInvoices() {
    logger.debug("removeAllInvoices called");
    List<Integer> idList = this.getAllIds();
    List<Invoice> removedInvoices = new ArrayList<>();
    idList.forEach(integer -> removedInvoices.add(this.removeInvoiceById(integer)));
    return removedInvoices;
  }
  
  @Override
  public List<Integer> getAllIds() {
    logger.debug("getAllIds called");
    List<Integer> idList = new ArrayList<>();
    database.forEach(invoice -> idList.add(invoice.getInvoiceId()));
    Collections.sort(idList);
    return idList;
  }
  
  @Override
  public boolean dropDatabase() {
    logger.debug("dropDatabase called");
    this.removeAllInvoices();
    return this.getAllInvoices().size() == 0;
  }
  
  
  @Override
  public String toString() {
    logger.debug("toString called");
    return "InMemory\tdatabase:\n"
        + database;
  }

  @Override
  public boolean equals(Object object) {
    logger.debug("equals called");
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
    logger.debug("hashCode called");
    int result = database != null ? database.hashCode() : 0;
    result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
    return result;
  }
}
