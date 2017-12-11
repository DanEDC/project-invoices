package pl.coderstrust.db.impl.file;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.time.LocalDate;
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
    System.out.println(inFileDb.getName());
    System.out.println(inFileId.getName());
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
    return false;
  }
  
  @Override
  public Invoice getInvoice(Integer invoiceId) {
    Invoice invoice = null;
    List<byte[]> stringList = fileHelper.readAsListOfByteArray(inFileDb);
    for (byte[] aStringList : stringList) {
      Invoice candidate = (Invoice) jsonConverter.jsonToObject(aStringList);
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoice = candidate;
        break;
      }
    }
    return invoice;
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