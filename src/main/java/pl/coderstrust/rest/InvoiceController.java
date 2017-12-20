package pl.coderstrust.rest;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

import java.util.List;

@Api(value = "/invoices", description = "Operations on invoices")

@RestController
public class InvoiceController {


  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceController(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @GetMapping(value = "/invoices")
  public List<Invoice> getInvoices() {
    return invoiceBook.getAllInvoices();
  }

  @GetMapping(value = "/invoices/{id}")
  public Invoice getInvoice(@PathVariable int id) {
    return invoiceBook.getInvoice(id);
  }

  @PostMapping(value = "/invoices")
  public int saveInvoice(Invoice invoice) {
    return 0;
  }

  //NOT working, in development
  @DeleteMapping(value = "/invoices{id}")
  public int removeInvoice(@PathVariable int id) {
    return 0;
  }

}

