package pl.coderstrust.db.impl.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.model.Invoice;

@Service
class FileNameManager {
  
  private static Logger logger = LoggerFactory.getLogger(FileNameManager.class);
  
  public FileNameManager() {
    logger.debug("FileNameManager initiated");
  }
  
  public String getFileLocation(Invoice invoice) {
    return invoice.getDate().getYear() + "";
  }
  
  public String getFileName(Invoice invoice) {
    return
        invoice.getDate().getMonthValue()
        + "_"
        + invoice.getDate().getMonth()
        + ".inv";
  }
}
