package pl.coderstrust.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Database extends CrudRepository<Invoice, Long> {

  Integer getNextInvoiceId();

  boolean saveInvoice(Invoice invoice);

  Invoice getInvoiceById(Integer invoiceId);
  
  List<Invoice> getInvoicesFromDateToDate(LocalDate from, LocalDate to);

  List<Invoice> getAllInvoices();
  
  Invoice removeInvoiceById(Integer invoiceId);
  
  List<Invoice> removeAllInvoices();
  
  List<Integer> getAllIds();
  
  boolean dropDatabase();
  
}
