package pl.coderstrust.db.impl.file;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
public class InFile implements Database {

  private String defaultInFileName = "InFile-Invoices"; //+ LocalDate.now().getYear();
  private File inFileDb = new File(defaultInFileName);

  private String inFileAssistingName = defaultInFileName + "_id";
  private File inFileId = new File(inFileAssistingName);

  private final AtomicReference<Integer> invoiceId = new AtomicReference<>(0);

  private FileHelper fileHelper;
  private JsonConverter jsonConverter;

  public InFile(FileHelper fileHelper, JsonConverter jsonConverter) {
    this.fileHelper = fileHelper;
    this.jsonConverter = jsonConverter;
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

    String idAsString = jsonConverter.objectToJson(nextId);
    fileHelper.overwriteFile(inFileId, idAsString);

    return nextId;
  }

  @Override
  public boolean saveInvoice(Invoice invoice) {
    String invoiceAsString = jsonConverter.objectToJson(invoice);
    fileHelper.appendFile(inFileDb, invoiceAsString);
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
    List<String> inputStringList = fileHelper.readAsStringList(inFileDb);
    List<String> outputStringList = new ArrayList<>();
    boolean invoiceDeleted = false;

    for (String string : inputStringList) {
      Invoice candidate = jsonConverter.jsonStringToInvoice(string);
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoiceDeleted = true;
      } else {
        outputStringList.add(string);
      }
    }
    if (invoiceDeleted) {
      fileHelper.clearFile(inFileDb);
      outputStringList.forEach(s -> fileHelper.appendFile(inFileDb, s));
    }
    return invoiceDeleted;
  }
}