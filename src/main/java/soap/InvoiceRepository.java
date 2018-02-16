package soap;

import io.spring.guides.gs_producing_web_service.Invoice;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@Component
public class InvoiceRepository {

  private static final Map<String, Invoice> invoices = new HashMap<>();

  @PostConstruct
  public void initData() {
    Invoice invoice = new Invoice();
    invoice.setInvoiceID(1);
    invoice.setDate(2002 - 09 - 24);
    invoice.setSeller("Mat");
    invoice.setBuyer("Buyer");
    invoice.setItems();

    invoices.put(invoice.getInvoiceID(), invoice);

    Country poland = new Country();
    poland.setName("Poland");
    poland.setCapital("Warsaw");
    poland.setCurrency(Currency.PLN);
    poland.setPopulation(38186860);

    countries.put(poland.getName(), poland);

    Country uk = new Country();
    uk.setName("United Kingdom");
    uk.setCapital("London");
    uk.setCurrency(Currency.GBP);
    uk.setPopulation(63705000);

    countries.put(uk.getName(), uk);
  }

  public Invoice findInvoice(String name) {
    Assert.notNull(name, "The invoice ID must not be null");
    return invoices.get(name);
  }
}

}
