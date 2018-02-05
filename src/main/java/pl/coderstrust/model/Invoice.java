package pl.coderstrust.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Invoice implements Comparable<LocalDate> {
  
  private static Logger logger = LoggerFactory.getLogger(Invoice.class);
  
  private Integer invoiceId;
  private LocalDate date;
  private Company seller;
  private Company buyer;

  private List<Item> items = new ArrayList<>();

  public Invoice() {
  }

  public Invoice(LocalDate date, Company seller, Company buyer,
      List<Item> items) {
    this.invoiceId = null;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(items);
  }

  public Integer getInvoiceId() {
    return invoiceId;
  }
  
  public final void setInvoiceId(Integer invoiceId) {
    Integer oldId = this.invoiceId;
    this.invoiceId = invoiceId;
    if (oldId != null && oldId != 0) {
      logger.info("Changed Invoice Id: " + oldId + " to " + invoiceId);
    }
  }
  
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  public Company getSeller() {
    return seller;
  }
  
  public void setSeller(Company seller) {
    this.seller = seller;
  }
  
  public Company getBuyer() {
    return buyer;
  }
  
  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public List<Item> getItems() {
    return items;
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
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Invoice)) {
      return false;
    }
    Invoice invoice = (Invoice) object;
    return Objects.equals(getInvoiceId(), invoice.getInvoiceId())
        && Objects.equals(getDate(), invoice.getDate())
        && Objects.equals(getSeller(), invoice.getSeller())
        && Objects.equals(getBuyer(), invoice.getBuyer())
        && Objects.equals(getItems(), invoice.getItems());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getInvoiceId(), getDate(), getSeller(), getBuyer(), getItems());
  }
  
  @Override
  public int compareTo(LocalDate date) {
    return this.getDate().compareTo(date);
  }
}
