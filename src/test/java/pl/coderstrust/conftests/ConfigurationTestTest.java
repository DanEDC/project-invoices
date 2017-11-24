package pl.coderstrust.conftests;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ConfigurationTestTest {

  @Test
  public void shouldWork() {
    int[] var1 = {0, 5, 8};
    int[] var2 = {0, 5, 8};

    assertArrayEquals(var1, var2);
  }

}