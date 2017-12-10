package pl.coderstrust.model;

public class Company {
  
  private String name;
  private String vatId;
  
  public Company(String name, String vatId) {
    this.name = name;
    this.vatId = vatId;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getVatId() {
    return vatId;
  }
  
  public void setVatId(String vatId) {
    this.vatId = vatId;
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Company)) {
      return false;
    }
    Company company = (Company) object;
    return (getName() != null ? getName().equals(company.getName()) : company.getName() == null)
        && (getVatId() != null ? getVatId()
        .equals(company.getVatId())
        : company.getVatId() == null);
  }
  
  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result =
        31 * result + (getVatId() != null ? getVatId()
            .hashCode()
            : 0);
    return result;
  }
  
  @Override
  public String toString() {
    return name+".co";

  }
}
