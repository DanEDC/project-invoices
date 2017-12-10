package pl.coderstrust.db.impl.file;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InFile implements Database {
  
  private String defaultInFileName = "InFile-Invoices" + LocalDate.now().getYear();
  private File inFile = new File(defaultInFileName);
  private String inFileAssistingName = inFile.getName().replace(".", "_id.");
  private File inFileId = new File(defaultInFileName);
  private final AtomicReference<Integer> invoiceId = new AtomicReference<>(0);
  
  public InFile() {
  }
  
  public InFile(String inFileName, String inFileAssistingName) {
    this.inFile = new File(inFileName);
    this.inFileId = new File(inFileAssistingName);
  }
  
  @Override
  public Integer getNextInvoiceId() {
    invoiceId.getAndSet(invoiceId.get() + 1);
    return invoiceId.get();
  }
  
  
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    return false;
  }
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    return null;
  }
  
  @Override
  public List<Invoice> getAllInvoices() {
    return null;
  }
  
  @Override
  public boolean removeInvoice(Integer invoiceId) {
    return false;
  }
}