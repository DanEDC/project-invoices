package pl.coderstrust.db.impl.file;

import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceId;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class OnDriveDatabase implements Database {
  
  private JsonConverter jsonConverter = new JsonConverter();
  private FileHelper fileHelper = new FileHelper();
  
  private String invoiceFileName =
      "src\\main\\resources\\Invoices"
          + LocalDate.now().getMonthValue()
          + LocalDate.now().getYear()
          + ".txt";
  private File invoicesFile = new File(invoiceFileName);
  
  private String fileLastId = invoicesFile.getPath().replace(".", "_id.");
  private File lastInvoiceIdHolder = new File(fileLastId);
  
  
  @Override
  public boolean saveInvoice(Invoice invoice) throws IOException {
    
    if (lastInvoiceIdHolder.exists() && invoice.getInvoiceId() != getLastInvoiceId()) {
      invoice.setInvoiceId(invoice.getInvoiceId().addToId(getLastInvoiceId()));
    }
    
    fileHelper.append(invoicesFile, jsonConverter.objectToJson(invoice));
    
    fileHelper.overwrite(lastInvoiceIdHolder, jsonConverter
        .objectToJson(invoice
            .getInvoiceId()));
    
    System.out.println("Invoice #"
        + invoice.getInvoiceId()
        + " -> saved to file -> "
        + invoicesFile);
    return true;
  }
  
  
  @Override
  public boolean removeInvoice(Invoice invoice) {
    return false;
  }
  
  private InvoiceId getLastInvoiceId() {
    InvoiceId lastId = null;
    String string = fileHelper.readObject(lastInvoiceIdHolder);
    try {
      lastId = jsonConverter.jsonToObject(string);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lastId;
  }
}