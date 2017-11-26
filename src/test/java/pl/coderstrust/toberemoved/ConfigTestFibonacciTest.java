package pl.coderstrust.toberemoved;

import static org.junit.Assert.assertEquals;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ConfigTestFibonacciTest {
  
  @Test
  public static void shouldUseDefaultConstructor() {
    //    given
    String expectedObjectName = "class pl.coderstrust.toberemoved.ConfigTestFibonacci";
    
    //    when
    Object createdByDefaultConstructor = new ConfigTestFibonacci();
    String resultObjectName = createdByDefaultConstructor.getClass().toString();
    
    //    then
    assertEquals(expectedObjectName, resultObjectName);
  }
  
  @Test
  public static void shouldInvokeMain() {
    //    given
    
    //    when
    ConfigTestFibonacci.main(null);
    //    then
    assertEquals(0, 0);
    
  }
  
  @Test
  @Parameters({"0,0", "1,1", "2,1", "5,5", "10,55"})
  public static void shouldReturnExpectedNumberForGivenIndex(int index, int expected) {
    
    //    given
    
    //    when
    
    int result = (int) ConfigTestFibonacci.fibonacci(index);
    
    //    then
    
    assertEquals(expected, result);
  }
  
  
}
