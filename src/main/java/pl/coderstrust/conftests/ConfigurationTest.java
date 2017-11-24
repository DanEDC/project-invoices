package pl.coderstrust.conftests;

import java.util.Arrays;

public class ConfigurationTest {

  /**
   * Some comment here
   */

  public static void main(String[] args) {
    System.out.println("Hello World!");
    System.out.println("Hello Marcin");

    int[] var1 = {0, 5, 8};
    int[] var2 = {0, 5, 8};

    assert (Arrays.equals(var1, var2));
  }

}
