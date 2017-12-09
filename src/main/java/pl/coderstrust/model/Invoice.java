package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
  
  private Integer invoiceId;
  private LocalDate date;
  private Company seller;
  private Company buyer;
  
  private List<Item> items = new ArrayList<>();
  
  public Invoice(LocalDate date, Company seller, Company buyer,
      List<Item> items) {
    this.invoiceId = null;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(items);
  }
  
  public final void setInvoiceId(Integer invoiceId) {
    this.invoiceId = invoiceId ;
  }
  
  public Integer getInvoiceId() {
    return invoiceId;
  }
  
  public LocalDate getDate() {
    return date;
  }
  
  public Company getSeller() {
    return seller;
  }
  
  public Company getBuyer() {
    return buyer;
  }
  
  public List<Item> getItems() {
    return items;
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
