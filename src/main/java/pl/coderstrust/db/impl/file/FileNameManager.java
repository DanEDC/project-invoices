package pl.coderstrust.db.impl.file;

import org.springframework.stereotype.Service;
import pl.coderstrust.model.Invoice;

@Service
class FileNameManager {
  
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
