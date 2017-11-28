package pl.coderstrust.model;

public class InvoiceId {
  
  /**
   * https://stackoverflow.com/questions/25852227/implementing-class-with-unique-identification-number
   */

  private static int count = 1;
  private final int id;
  
  public InvoiceId() {
    id = count;
    count++;
  }
  
  @Override
  public String toString() {
    return "InvoiceId=" + id;
  }
}
