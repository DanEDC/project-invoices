import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class Tests01Test {

  @Test
  public void shouldWork() {
    int[] var01 = {0, 5, 3};
    int[] var02 = {4, 2, 1};
    assertArrayEquals(var01, var02);
  }

}