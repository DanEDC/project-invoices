package pl.coderstrust.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.db.impl.file.InFile;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

import java.util.List;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  /*service here!!*///TODO
  InvoiceBook invoiceBook = new InvoiceBook(new InFile());
  
  @GetMapping
  public List<Invoice> getInvoices() {
    return invoiceBook.getAllInvoices();
  }
  
  @GetMapping(value = "/{id}")
  public Invoice getInvoice(@PathVariable int id) {
    return invoiceBook.getInvoice(id);
  }
  
}