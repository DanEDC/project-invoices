package pl.coderstrust.db.impl.file;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InFile implements Database {
  
  private String defaultInFileName = "InFile-Invoices" + LocalDate.now().getYear();
  private File inFileDb = new File(defaultInFileName);
  
  private String inFileAssistingName = defaultInFileName + "_id";
  private File inFileId = new File(inFileAssistingName);
  
  private final AtomicReference<Integer> invoiceId = new AtomicReference<>(0);
  
  private FileHelper fileHelper = new FileHelper();
  private JsonConverter jsonConverter = new JsonConverter();
  
  
  public InFile() {
  }
  
  
  @Override
  public Integer getNextInvoiceId() {
    Integer nextId;
  
    if (inFileId.exists()) {
      String id = (fileHelper.readAsStringList(inFileId).get(0));
      nextId = Integer.parseInt(id);
    } else {
      nextId = invoiceId.get();
    }
    nextId++;
    return nextId;
  }
  
  @Override
  public boolean saveInvoice(Invoice invoice) {
    String invoiceAsString = jsonConverter.objectToJson(invoice);
    fileHelper.appendEndLine(inFileDb, invoiceAsString);
  
    String idAsString = jsonConverter.objectToJson(invoice.getInvoiceId());
    fileHelper.overwriteFirstLine(inFileId, idAsString);
    return true;
  }
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    Invoice invoice = null;
    List<String> stringList = fileHelper.readAsStringList(inFileDb);
    for (String string : stringList) {
      Invoice candidate = jsonConverter.jsonStringToInvoice(string);
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoice = candidate;
        break;
      }
    }
    return invoice;
  }
  
  @Override
  public List<Invoice> getAllInvoices() {
    List<String> stringList = fileHelper.readAsStringList(inFileDb);
    List<Invoice> invoices = new ArrayList<>();
    for (String string : stringList) {
      Invoice invoice = jsonConverter.jsonStringToInvoice(string);
      invoices.add(invoice);
    }
    return invoices;
  }
  
  @Override
  public boolean removeInvoice(Integer invoiceId) {
    return false;
  }
}