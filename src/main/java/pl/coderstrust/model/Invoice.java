package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Invoice {

  private Integer invoiceId;
  private LocalDate date;
  private Company seller;
  private Company buyer;

  private List<Item> items = new ArrayList<>();

  public Invoice() {
  }

  public Invoice(Integer invoiceId, LocalDate date, Company seller,
      Company buyer, List<Item> items) {
    this.invoiceId = invoiceId;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items = items;
  }

  public Invoice(LocalDate date, Company seller, Company buyer,
      List<Item> items) {
    this.invoiceId = null;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(items);

  }

  public final void setInvoiceId(Integer invoiceId) {
    this.invoiceId = invoiceId;
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

  public Collection<Item> getItems() {
    return items;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setSeller(Company seller) {
    this.seller = seller;
  }

  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
  
  @Override
  public String toString() {
    return "\n"
        + invoiceId
        + "," + date
        + "," + seller
        + "->" + buyer
        + ":" + items;
  }
}
