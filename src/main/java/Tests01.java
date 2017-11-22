import java.util.Arrays;

public class Tests01 {

  public static void main(String[] args) {
    System.out.println("Hello Marcin");
    System.out.println("Hello World");

    int[] var01 = {0, 5, 3};
    int[] var02 = {4, 2, 1};

    assert (Arrays.equals(var01, var02));
  }
}
