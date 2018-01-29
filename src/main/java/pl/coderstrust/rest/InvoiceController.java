package pl.coderstrust.rest;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Api(value = "/invoices", description = "Operations on invoices")

@RestController
@SuppressWarnings("unused")
public class InvoiceController {


  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceController(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @GetMapping(value = "id>0: single | 0 all")
  public List<Invoice> getAllInvoices() {
    return invoiceBook.getAllInvoices();
  }

  @GetMapping(value = "/invoices/{id}")
  public Invoice getInvoiceById(@PathVariable int id) {
    return invoiceBook.getInvoiceById(id);
  }
  
  @GetMapping(value = "/invoices/getByDate/{since,to}")
  public List<Invoice> getInvoicesFromDateToDate(
      @PathVariable("since") @DateTimeFormat(pattern = "yyyy-MM-dd") Date since,
      @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
    return invoiceBook.getInvoicesFromDateToDate(since, to);
  }
  
  @PostMapping(value = "/invoices")
  public Integer saveInvoice(@RequestBody Invoice invoice) {
    return invoiceBook.saveInvoice(invoice);
  }
  
  @PostMapping(value = "/invoices/invoicesList")
  public List<Integer> saveInvoices(@RequestBody Collection<Invoice> invoices) {
    return invoiceBook.saveInvoices(invoices);
  }

  @DeleteMapping(value = "/invoices/{id}")
  public Invoice removeInvoice(@PathVariable int id) {
    return invoiceBook.removeInvoice(id);
  }
  
  @DeleteMapping(value = "/invoices/")
  public boolean dropDatabase() {
    return invoiceBook.dropDatabase();
  }
  
}

