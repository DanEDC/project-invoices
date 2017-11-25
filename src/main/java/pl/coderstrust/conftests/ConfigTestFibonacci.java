package pl.coderstrust.conftests;


public class ConfigTestFibonacci {



  public static void main(String[] args) {
    System.out.println(fibonacci(10));

  }

  /**
   * Method fibonacci calculates Array of fibonacci numbers.
   */
  public static long fibonacci(int orderedNumber) {

    long[] fibonacciNumbersArray = new long[orderedNumber + 2];
    fibonacciNumbersArray[1] = 1;

    for (int i = 2; i <= orderedNumber; i++) {
      fibonacciNumbersArray[i] = fibonacciNumbersArray[i - 1] + fibonacciNumbersArray[i - 2];
    }
    return fibonacciNumbersArray[orderedNumber];
  }
}