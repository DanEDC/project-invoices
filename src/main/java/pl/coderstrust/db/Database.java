package pl.coderstrust.db;

import java.util.List;
import pl.coderstrust.model.Invoice;

public interface Database {

  Integer getNextInvoiceId();

  boolean saveInvoice(Invoice invoice);

  Invoice getInvoice(Integer invoiceId);

  List<Invoice> getAllInvoices();

  boolean removeInvoice(Integer invoiceId);


}
