package pl.coderstrust.model;

public enum Vat {
  vatFree("0"),
  vat5("5"),
  vat8("8"),
  vat23("23");

  public String vat;

  Vat(String vat) {
    this.vat = vat;
  }

  public void setVat(String vat) {
    this.vat = vat;
  }

  @Override
  public String toString() {
    return vat + "";
  }

  public String getVat() {
    return vat;
  }
}
