package pl.coderstrust.rest;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.config.EmailService;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Api(value = "/invoices", description = "Operations on invoices")

@RestController
@SuppressWarnings("unused")
public class InvoiceController {
  
  private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

  private InvoiceBook invoiceBook;
  private EmailService invoiceInfoMail;

  @Autowired
  public InvoiceController(InvoiceBook invoiceBook, EmailService invoiceInfoMail) {
    logger.info("InvoiceController initiated");
    this.invoiceBook = invoiceBook;
    this.invoiceInfoMail = invoiceInfoMail;
  }

  @GetMapping(value = "/invoices")
  public List<Invoice> getAllInvoices() {
    logger.debug("getAllInvoices called");
    return invoiceBook.getAllInvoices();
  }

  @GetMapping(value = "/invoices/{id}")
  public Invoice getInvoiceById(@PathVariable int id) {
    logger.debug("getInvoiceById called");
    return invoiceBook.getInvoiceById(id);
  }

  @GetMapping(value = "/invoices/")
  public List<Invoice> getInvoicesFromDateToDate(
      @RequestParam("since") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate since,
      @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
    logger.debug("getInvoicesFromDateToDate called");
    return invoiceBook.getInvoicesFromDateToDate(since, to);
  }

  @PostMapping(value = "/invoices")
  public Integer saveInvoice(@RequestBody Invoice invoice) {
    logger.debug("saveInvoice called");
    String invoiceMessage =
        "Invoice ID: " + invoice.getInvoiceId() + "\n- Buyer: " + invoice.getBuyer()
            + "\n- Seller: " + invoice.getSeller() + "\n- Items:\n" + invoice.getItems();
    invoiceInfoMail.sendSimpleMessage("mna@g.pl", "Invoice Added", invoiceMessage);
    return invoiceBook.saveInvoice(invoice);
  }

  @PostMapping(value = "/invoices/invoicesList")
  public List<Integer> saveInvoices(@RequestBody Collection<Invoice> invoices) {
    logger.debug("saveInvoices called");
    return invoiceBook.saveInvoices(invoices);
  }

  @DeleteMapping(value = "/invoices/{id}")
  public Invoice removeInvoiceById(@PathVariable int id) {
    logger.debug("removeInvoice called");
    return invoiceBook.removeInvoiceById(id);
  }

  @DeleteMapping(value = "/invoices/")
  public List<Invoice> removeAllInvoices() {
    logger.debug("removeAllInvoices called");
    return invoiceBook.removeAllInvoices();
  }

  @Scheduled(cron = "0 32 17 * * ?")
  public void sendScheduledMail() {
    logger.debug("sendScheduledMail called");
    String dayMessage = "Day " + LocalDate.now().minusDays(1) + ": " + invoiceBook
        .getYesterdayInvoicesNo(LocalDate.now().minusDays(1)) + " invoices added.";
    invoiceInfoMail.sendSimpleMessage("mna@g.pl",
        "Yesterday Summary - Invoices", dayMessage);
  }
}

