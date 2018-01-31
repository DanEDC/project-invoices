package pl.coderstrust.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    logger.debug("getItemId called");
    return itemId;
  }

  public final void setItemId(Integer itemId) {
    logger.debug("setItemId called");
    this.itemId = itemId;
  }

  public String getDescription() {
    logger.debug("getDescription called");
    return description;
  }

  public double getValue() {
    logger.debug("getValue called");
    return value;
  }

  public Vat getVat() {
    logger.debug("getVat called");
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

    Item that = (Item) object;

    if (Double.compare(that.value, value) != 0) {
      return false;
    }
    if (vat != that.vat) {
      return false;
    }
    return description != null ? description.equals(that.description) : that.description == null;
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
