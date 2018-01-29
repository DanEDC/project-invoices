package pl.coderstrust.logic;

import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
  
  public List<Invoice> getInvoicesFromDateToDate(String from, String to) throws Exception {
    LocalDate dateFrom, dateTo;
    int fromDay, fromMonth, fromYear, toDay, toMonth, toYear;
    if (from.length() == to.length()) {
      
      
      switch (from.length()) {
        case 0:
          dateFrom = dateTo = LocalDate.now();
          break;
        case 2:
          fromDay = Integer.parseInt(from);
          fromMonth = LocalDate.now().getMonthValue();
          fromYear = LocalDate.now().getYear();
          
          toDay = Integer.parseInt(to);
          toMonth = LocalDate.now().getMonthValue();
          toYear = LocalDate.now().getYear();
          break;
          
          break;
        case 5:
          fromDay = Integer.parseInt((from.replaceAll("^\\d\\d\\p{Punct}*","")));
          fromMonth = Integer.parseInt((from.replaceAll("\\p{Punct}*\\d\\d","")));
          
          toDay = Integer.parseInt(to);
          
      }
    } else {
      throw new Exception(
          "Wrong or incompatibile date format. \"DD\" or \"MM-DD\" or \"YYYY-MM-DD\".");
    }
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
}
