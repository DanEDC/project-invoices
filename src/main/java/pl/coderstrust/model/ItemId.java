package pl.coderstrust.model;

public class ItemId {
  
  /**
   * https://stackoverflow.com/questions/25852227/implementing-class-with-unique-identification-number
   */
  
  private static int count = 1;
  private final int id;
  
  public ItemId() {
    id = count;
    count++;
  }
  
  @Override
  public String toString() {
    return "ItemId=" + id;
  }
}
