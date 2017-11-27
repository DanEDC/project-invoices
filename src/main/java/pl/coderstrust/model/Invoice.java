package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Invoice {
  
  private InvoiceId invoiceId;
  private LocalDate date;
  private Company seller;
  private Company buyer;
  
  private List<Item> items = new ArrayList<>();
  private Vat vat;
  
  public Invoice(LocalDate date, Company seller, Company buyer,
      Item[] items, Vat vat) {
    this.invoiceId = new InvoiceId();
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(Arrays.asList(items));
    this.vat = vat;
  }
  
  @Override
  public String toString() {
    return "\n"
        + invoiceId
        + ", date=" + date
        + ", seller=" + seller
        + ", buyer=" + buyer
        + ", items=" + items;
  }
}
