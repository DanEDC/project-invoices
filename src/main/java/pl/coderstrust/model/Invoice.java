package pl.coderstrust.model;

import pl.coderstrust.db.impl.InvoiceId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Invoice {
  
  private InvoiceId id;
  private LocalDate date;
  private Company seller;
  private Company buyer;
  
  private List<Item> items = new ArrayList<>();
  private Vat vat;
  
  public Invoice(LocalDate date, Company seller, Company buyer,
      Item[] items, Vat vat) {
    this.id = new InvoiceId();
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(Arrays.asList(items));
    this.vat = vat;
  }
  
  @Override
  public String toString() {
    return "\nInvoice:{"
        + "id:" + id
        + ", date=" + date
        + ", seller=" + seller
        + ", buyer=" + buyer
        + ", items=" + items
        + '}';
  }
}
