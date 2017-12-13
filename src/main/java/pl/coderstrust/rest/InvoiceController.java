package pl.coderstrust.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

//OD MATEUSZA:
//@RestController
//@RequestMapping("/invoices")
//public class InvoiceController {
//  /*service here!!*///TODO
//  InvoiceBook invoiceBook = new InvoiceBook(new InFile());
//
//  @GetMapping
//  public List<Invoice> getInvoices() {
//    return invoiceBook.getAllInvoices();
//  }
//
//  @GetMapping(value = "/{id}")
//  public Invoice getInvoice(@PathVariable int id) {
//    return invoiceBook.getInvoice(id);
//  }
@RestController
public class InvoiceController {

  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceController( InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @RequestMapping("/invoices")
  public List<Invoice> getInvoices() {
    return invoiceBook.getAllInvoices();
  }

  @GetMapping(value = "/{id}")
  public Invoice getInvoice(@PathVariable int id) {
    return invoiceBook.getInvoice(id);

  }
}