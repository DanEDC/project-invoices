package pl.coderstrust.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
    logger.debug("Invoice created");
  }

  public Invoice(LocalDate date, Company seller, Company buyer,
      List<Item> items) {
    logger.debug("Invoice created");
    this.invoiceId = null;
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.items.addAll(items);
  }

  public Integer getInvoiceId() {
    logger.debug("getInvoiceId called");
    return invoiceId;
  }
  
  public final void setInvoiceId(Integer invoiceId) {
    logger.debug("setInvoiceId called");
    this.invoiceId = invoiceId;
  }
  
  public LocalDate getDate() {
    logger.debug("getDate called");
    return date;
  }

  public void setDate(LocalDate date) {
    logger.debug("setDate called");
    this.date = date;
  }
  
  public Company getSeller() {
    logger.debug("getSeller called");
    return seller;
  }
  
  public void setSeller(Company seller) {
    logger.debug("setSeller called");
    this.seller = seller;
  }
  
  public Company getBuyer() {
    logger.debug("getBuyer called");
    return buyer;
  }
  
  public void setBuyer(Company buyer) {
    logger.debug("setBuyer called");
    this.buyer = buyer;
  }
  
  public Collection<Item> getItems() {
    logger.debug("getItems called");
    return items;
  }

  public void setItems(List<Item> items) {
    logger.debug("setItems called");
    this.items = items;
  }
  
  @Override
  public String toString() {
    logger.debug("toString called");
    return "\n"
        + invoiceId
        + "," + date
        + "," + seller
        + "->" + buyer
        + ":" + items;
  }
  
  @Override
  public boolean equals(Object object) {
    logger.debug("equals called");
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
    logger.debug("hashCode called");
    return Objects.hash(getInvoiceId(), getDate(), getSeller(), getBuyer(), getItems());
  }
  
 
  @Override
  public int compareTo(LocalDate date) {
    logger.debug("compareTo called");
    return this.getDate().compareTo(date);
  }
}
