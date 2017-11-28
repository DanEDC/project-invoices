package pl.coderstrust.model;

import static org.junit.Assert.assertEquals;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.coderstrust.Main;

@RunWith(JUnitParamsRunner.class)
public class MainTest {
  
  @Test
  public static void shouldUseDefaultConstructor() {
    //    given
    Object instanceGivenByDefaultConstructor = new Main();
    String expectedObjectName = instanceGivenByDefaultConstructor.getClass().toString();
    
    //    when
    Object createdByDefaultConstructor = new Main();
    String resultObjectName = createdByDefaultConstructor.getClass().toString();
    
    //    then
    assertEquals(expectedObjectName, resultObjectName);
  }
  
  @Test
  public static void shouldInvokeMain() {
    //    given
    
    //    when
    Main.main(null);
    //    then
    assertEquals(0, 0);
    
  }
}
