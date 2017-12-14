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
public class InvoiceController {
  
  InvoiceBook invoiceBook = new InvoiceBook(new InFile());

//  @Autowired//TODO
//  public InvoiceController(InvoiceBook invoiceBook) {
//    this.invoiceBook = invoiceBook;
//  }
  
  @RequestMapping("/invoices")
  @GetMapping
  public List<Invoice> getInvoices() {
    return invoiceBook.getAllInvoices();
  }
  
  @RequestMapping(value = "/invoices/{id}")
  @GetMapping
  public Invoice getInvoice(@PathVariable int id) {
    return invoiceBook.getInvoice(id);
  }
  
}

