package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Invoice {
  
  private int id = 0;
  private LocalDate date;
  
  private Company seller;
  private Company buyer;
  
  private List<InvoiceItem> items = new ArrayList<>();
  private Vat vat;
  
  public Invoice(LocalDate date, Company seller, Company buyer,
      InvoiceItem[] items, Vat vat) {
    this.id = id++;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(Arrays.asList(items));
    this.vat = vat;
  }
  
  @Override
  public String toString() {
    return "Invoice:{"
        + "id=" + id
        + ", date=" + date
        + ", seller=" + seller
        + ", buyer=" + buyer
        + ", items=" + items
        + '}';
  }
}
