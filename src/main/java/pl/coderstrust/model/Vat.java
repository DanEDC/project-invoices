package pl.coderstrust.model;

import javax.xml.bind.annotation.XmlType;

import java.math.BigDecimal;

@XmlType
public enum Vat {
  vatFree(new BigDecimal(0)),
  vat5(new BigDecimal(5)),
  vat8(new BigDecimal(8)),
  vat23(new BigDecimal(23));

  private final BigDecimal vat;

  Vat(BigDecimal vat) {
    this.vat = vat;
  }

  @Override
  public String toString() {
    return vat + "";
  }

  public BigDecimal getVat() {
    return vat;
  }
}
