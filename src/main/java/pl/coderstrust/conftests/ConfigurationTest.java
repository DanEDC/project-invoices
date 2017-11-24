package pl.coderstrust.conftests;

import java.util.Arrays;

public class ConfigurationTest {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    System.out.println("Hello Marcin");

    int[] var1 = {0, 5, 8};
    int[] var2 = {3, 6, 10};

    assert(Arrays.equals(var1, var2));
  }

}
