package pl.coderstrust.db.impl.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
@ConditionalOnProperty(name = "pl.coderstrust.db.impl.DatabaseImpl", havingValue = "inFile")

public class InFile implements Database {
  
  private static Logger logger = LoggerFactory.getLogger(InFile.class);
  
  private final AtomicReference<Integer> invoiceId =
      new AtomicReference<>(0);
  
  private final String path;
  private final File database;
  private File inFileId;

  /**
   * Supporting classes
   */
  private FileHelper fileHelper;
  private FileNameManager fileNameManager;
  private JsonConverter jsonConverter;
  
  
  public InFile(FileHelper fileHelper,
      FileNameManager fileNameManager,
      JsonConverter jsonConverter,
      @Value("${pl.coderstrust.db.impl.file.databasePath}") String path) {
  
    this.path = path;

    this.database = new File(this.path);
    this.fileHelper = fileHelper;
    this.fileNameManager = fileNameManager;
    this.jsonConverter = jsonConverter;
    if (this.database.exists()) {
      logger.info("InFile Database exists");
    } else {
      logger.info("InFile Database initiated");
    }
  }
  
  @Override
  public Integer getNextInvoiceId() {
    fileHelper.createNewDir(path);
    inFileId = new File(path + "\\LastID.txt");
  
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
    logger.debug("saveInvoice called");
    File invoicesFile = getFileByInvoice(invoice);
    String invoiceAsString = jsonConverter.objectToJson(invoice);
    return fileHelper.appendFile(invoicesFile, invoiceAsString);
  }
  
  @Override
  public Invoice getInvoiceById(Integer invoiceId) {
    logger.debug("getInvoiceById called");
    File file = findFileByInvoiceId(invoiceId);
    if (file != null) {
      return getInvoiceFromFile(invoiceId, file);
    } else {
      logger.debug("Invoice of id " + invoiceId + " not found");
      return null;
    }
  }
  
  @Override//TODO: optimize this method, so it reads only files from given range of dates (not ALL!)
  public List<Invoice> getInvoicesFromDateToDate(LocalDate from, LocalDate to) {
    logger.debug("getInvoicesFromDateToDate called");
    List<Invoice> resultList = new ArrayList<>();
    List<Invoice> candidateList = this.getAllInvoices();
    for (Invoice invoice : candidateList) {
      if (invoice.compareTo(from) >= 0 && invoice.compareTo(to) <= 0) {
        resultList.add(invoice);
      }
    }
    return resultList;
  }
  
  @Override
  public List<Invoice> getAllInvoices() {
    logger.debug("getAllInvoices called");
    List<File> fileList = fileHelper.listSubDirContent(database);
  
    List<String> stringList = new ArrayList<>();
    fileList.forEach(file -> stringList.addAll(fileHelper.readAsStringList(file)));
  
    List<Invoice> invoices = new ArrayList<>();
    stringList.forEach(s -> invoices.add(jsonConverter.stringToInvoice(s)));
    return invoices;
  }
  
  @Override
  public Invoice removeInvoiceById(Integer invoiceId) {
    logger.debug("removeInvoiceById called");
    Invoice invoice = this.getInvoiceById(invoiceId);
    if (invoice != null) {
      if (removeInvoiceFromFile(getFileByInvoice(invoice), invoiceId)) {
        logger.info("Invoice " + invoiceId + " deleted");
        return invoice;
      }
    }
    return null;
  }
  
  @Override
  public List<Invoice> removeAllInvoices() {
    logger.debug("removeAllInvoices called");
    List<Invoice> invoices = this.getAllInvoices();
    if (deleteAllInvoiceFiles()) {
      logger.info("Delete all invoice files succeed");
    } else {
      logger.warn("Delete all invoice files failed");
    }
    return invoices;
  }
  
  @Override
  public List<Integer> getAllIds() {
    logger.debug("getAllIds called");
    List<Invoice> invoices = this.getAllInvoices();
    List<Integer> idList = new ArrayList<>();
    invoices.forEach(invoice -> idList.add(invoice.getInvoiceId()));
    return idList;
  }
  
  @Override
  public boolean dropDatabase() {
    logger.debug("dropDatabase called");
    this.removeAllInvoices();
    File[] files = database.listFiles();
    deleteFiles(files);
    return database.delete();
  }
  
  /**
   * Supporting Methods
   */
  private boolean removeInvoiceFromFile(File file, Integer invoiceId) {
    logger.debug("removeInvoiceFromFile called");
    boolean result = false;
    List<String> stringList = fileHelper.readAsStringList(file);
    for (String string : stringList) {
      if (jsonConverter.stringToInvoice(string).getInvoiceId().equals(invoiceId)) {
        stringList.remove(string);
        result = fileHelper.clearFile(file);
        fileHelper.appendFile(file, stringList);
        break;
      }
    }
    return result;
  }
  
  private File getFileByInvoice(Invoice invoice) {
    logger.debug("getFileByInvoice called");
    String dir = path + "\\" + fileNameManager.getFileLocation(invoice);//TODO remove if redundant
    fileHelper.createNewDir(dir);                                       //TODO remove if redundant
    return new File(dir + "\\" + fileNameManager.getFileName(invoice));
  }
  
  private boolean deleteAllInvoiceFiles() {
    logger.debug("deleteAllInvoiceFiles called");
    List<File> fileList = fileHelper.listSubDirContent(database);
    fileList.forEach(file -> fileHelper.clearFile(file));
    fileList.forEach(file -> fileHelper.deleteFile(file));
    File[] files = fileHelper.listDirContent(database, 1);
    return files == null || !deleteDirectoriesIfEmpty(files);
  }
  
  private boolean deleteDirectoriesIfEmpty(File[] files) {
    logger.debug("deleteDirectoriesIfEmpty called");
    if (files != null) {
      int deleted = 0;
      int failed = 0;
      for (File file : files) {
        if (fileHelper.deleteDirectoryIfEmpty(file)) {
          deleted++;
        } else {
          failed++;
        }
      }
      if (deleted == 0) {
        logger.info("Summary: deleted " + deleted
            + " out of " + deleted + failed + " directors");
      } else {
        logger.warn(
            "Summary: deleted " + deleted
                + " out of " + deleted + failed
                + " directors; " + deleted + " failed to delete");
      }
    }
    return false;
  }
  
  private void deleteFiles(File[] files) {
    logger.debug("deleteFiles called");
    if (files != null) {
      for (File file : files) {
        fileHelper.deleteFile(file);
      }
    }
  }
  
  
  private Invoice getInvoiceFromFile(Integer invoiceId, File file) {
    logger.debug("getInvoiceFromFile called");
    Invoice invoice = null;
    List<String> stringList = fileHelper.readAsStringList(file);
    for (String string : stringList) {
      Invoice candidate = jsonConverter.stringToInvoice(string);
      if (candidate.getInvoiceId().equals(invoiceId)) {
        invoice = candidate;
        break;
      }
    }
    if (invoice == null) {
      logger.error("");
    }
    return invoice;
  }
  
  private File findFileByInvoiceId(Integer invoiceId) {
    logger.debug("findFileByInvoiceId called");
    List<File> fileList = fileHelper.listSubDirContent(database);
    return findFileByInvoiceId(fileList, invoiceId);
  }
  
  private File findFileByInvoiceId(List<File> listOfFiles, Integer invoiceId) {
    logger.debug("findFileByInvoiceId called");
    File theFile = null;
    for (File candidate : listOfFiles) {
      if (findIdInFile(candidate, invoiceId)) {
        logger.info("Invoice " + invoiceId + " found in " + candidate);
        theFile = candidate;
      }
    }
    return theFile;
  }
  
  private boolean findIdInFile(File file, Integer invoiceId) {
    logger.debug("findIdInFile called");
    boolean answer;
    List<String> stringList = fileHelper.readAsStringList(file);
    answer = findIdInJsonList(invoiceId, stringList);
    return answer;
  }
  
  private boolean findIdInJsonList(Integer invoiceId, List<String> stringList) {
    logger.debug("findIdInJsonList called");
    Integer candidateId;
    boolean answer = false;
    for (String string : stringList) {
      candidateId = jsonConverter.stringToInvoice(string).getInvoiceId();
      if (candidateId.equals(invoiceId)) {
        logger.info("Invoice " + invoiceId + " exists");
        answer = true;
        break;
      }
    }
    return answer;
  }
}