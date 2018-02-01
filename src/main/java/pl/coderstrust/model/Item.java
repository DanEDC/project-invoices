package pl.coderstrust.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Item {
  
  private static Logger logger = LoggerFactory.getLogger(Item.class);

  private Integer itemId;
  private String description;
  private double value;
  private Vat vat;

  public Item() {
    logger.debug("Item created");
  }

  public Item(String description, double value, Vat vat) {
    logger.debug("Item created");
    this.itemId = null;
    this.description = description;
    this.value = value;
    this.vat = vat;
  }

  public Integer getItemId() {
    return itemId;
  }

  public final void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public String getDescription() {
    return description;
  }

  public double getValue() {
    return value;
  }

  public Vat getVat() {
    return vat;
  }
  
  @Override
  public boolean equals(Object object) {
    logger.debug("equals called");
    if (this == object) {
      return true;
    }
    if (!(object instanceof Item)) {
      return false;
    }
    Item item = (Item) object;
    return Double.compare(item.getValue(), getValue()) == 0
        && Objects.equals(getItemId(), item.getItemId())
        && Objects.equals(getDescription(), item.getDescription())
        && getVat() == item.getVat();
  }
  
  @Override
  public int hashCode() {
    logger.debug("hashCode called");
    int result;
    long temp;
    result = description != null ? description.hashCode() : 0;
    temp = Double.doubleToLongBits(value);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (vat != null ? vat.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    logger.debug("toString called");
    return itemId
        + ":" + description
        + "," + value
        + "$," + vat + "%";
  }
}
