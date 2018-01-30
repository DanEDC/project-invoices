package pl.coderstrust.logic;

import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }
  
  
  /**
   * Application-end methods - distributing request to
   * proper methods.
   */
  
  //TODO if this method can't work as universal get, then remove!
  public List<Invoice> getInvoice(List<Integer> ids) {
    if (ids.size() == 1) {
      int id = ids.get(0);
      switch (id) {
        case 0:
          return this.getAllInvoices();
        default:
          List<Invoice> invoices = new ArrayList<>();
          invoices.add(this.getInvoiceById(id));
          return invoices;
      }
    } else {
      return this.getListOfInvoiceById(ids);
    }
  }
  
  public Integer saveInvoice(Invoice invoice) {
    Integer id = database.getNextInvoiceId();
    invoice.setInvoiceId(id);
    if (database.saveInvoice(invoice)) {
      System.out.println("Invoice saved");
      return id;
    } else {
      System.out.println("Invoice not saved");
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
  
  /**
   * Method modified fom example:
   * https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
   */
  public static LocalDate parseDate(String date) {
    System.out.println(date);
    String[] dateSlitted = date.split("-");
    int year = Integer.parseInt(dateSlitted[0]);
    System.out.println(year);
    int month = Integer.parseInt(dateSlitted[1]);
    System.out.println(month);
    int day = Integer.parseInt(dateSlitted[2]);
    System.out.println(day);
    return LocalDate.of(year, month, day);
    
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
  
  public boolean dropDatabase() {
    return database.dropDatabase();
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
