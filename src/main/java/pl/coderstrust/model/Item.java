package pl.coderstrust.model;

import java.util.Objects;


public class Item {


  private Integer itemId;
  private String description;
  private double value;
  private String vat;

  public Item() {
  }

  public Item(String description, double value, String vat) {
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

  public void setDescription(String description) {
    this.description = description;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public String getVat() {
    return vat;
  }

  public void setVat(String vat) {
    this.vat = vat;
  }

  @Override
  public boolean equals(Object object) {
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
    return itemId
        + ":" + description
        + "," + value
        + ":" + vat;
  }

}
