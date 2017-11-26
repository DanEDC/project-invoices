package pl.coderstrust.model;

public class InvoiceItem {

  private String description;
  private double value;
  private Vat vat;
  
  public InvoiceItem(String description, double value, Vat vat) {
    this.description = description;
    this.value = value;
    this.vat = vat;
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof InvoiceItem)) {
      return false;
    }
    
    InvoiceItem that = (InvoiceItem) object;
    
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
    return "InvoiceItem{"
        + "description='"
        + description + '\''
        + ", value=" + value
        + ", vat=" + vat + '}';
  }
}
