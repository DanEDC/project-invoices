package pl.coderstrust.db.impl.file;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InFile implements Database {
  
  private String defaultInFileName = "InFile-Invoices" + LocalDate.now().getYear();
  private File inFile = new File(defaultInFileName);
  private String inFileAssistingName = defaultInFileName + "_id";
  private File inFileId = new File(inFileAssistingName);
  private final AtomicReference<Integer> invoiceId = new AtomicReference<>(0);
  private FileHelper fileHelper = new FileHelper();
  private JsonConverter jsonConverter = new JsonConverter();
  
  
  public InFile() {
    System.out.println(inFile.getName());
    System.out.println(inFileId.getName());
  }
  
  public InFile(String inFileName) {
    this.inFile = new File(inFileName + LocalDate.now().getYear());
    this.inFileId = new File(inFileName + LocalDate.now().getYear() + "_id");
    
    
  }
  
  @Override
  public Integer getNextInvoiceId() {
    if (inFileId.exists()) {
      return Integer.parseInt(fileHelper.readObject(inFileId)) + 1;
    } else {
      invoiceId.getAndSet(invoiceId.get() + 1);
      return invoiceId.get();
    }
  }
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
  
    try {
      fileHelper.append(
          inFile, jsonConverter.objectToJson(invoice));
    
      fileHelper.overwrite(
          inFileId, jsonConverter.objectToJson(invoice.getInvoiceId()));
    } catch (IOException e) {
      e.printStackTrace();
    }
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