package pl.coderstrust.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.coderstrust.helpers.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(JUnitParamsRunner.class)
public abstract class DatabaseTest {
  
  public abstract Database provideImplementation();
  
  /**
   * Invoice in tests should be a mock?
   */
  @SuppressWarnings("all")
  private boolean invoiceIsMock = false;
  
  private InvoiceGenerator invGen = new InvoiceGenerator();
  
  
  /**
   * Parameter used only to see if implementation starts with
   * invoiceId = 1 at each fresh start.
   */
  @Test
  @SuppressWarnings("unused")
  @Parameters({"1", "3"})
  public void shouldGetNextInvoiceId(int param) {
    for (int loop = 0; loop <= param; loop++) {
      // given
      Database database = provideImplementation();
  
      Integer expected1 = 1;
      Integer expected2 = 2;
      Integer expected3 = 3;
  
      // when
      Integer invoice1Id = database.getNextInvoiceId();
      Integer invoice2Id = database.getNextInvoiceId();
      Integer invoice3Id = database.getNextInvoiceId();
  
      // then
      assertEquals(expected1,invoice1Id);
      assertEquals(expected2,invoice2Id);
      assertEquals(expected3,invoice3Id);
  
      assertTrue(database.dropDatabase());
    }
  }
  
  
  @Test
  public void shouldReturnTrueAfterSavingInvoice() {
    // given
    Database database = provideImplementation();
    Invoice invoice = invGen.getInvoice(false);
  
    // when
    invoice.setInvoiceId(database.getNextInvoiceId());
    boolean result = database.saveInvoice(invoice);
    
    // then
    assertTrue(result);
    assertTrue(database.dropDatabase());
  
  }
  
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void shouldGiveIdSaveAndReturnInvoiceById(int number) {
    //given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    Database database = provideImplementation();
    
    //when
    setIdAndSaveInvoice(invoices, database);
    
    //then
    for (int loop = 0; loop < number; loop++) {
      assertEquals(invoices.get(loop).getInvoiceId(),
          database.getInvoiceById((loop + 1)).getInvoiceId());
      assertEquals(invoices.get(loop).toString(), database.getInvoiceById(loop + 1).toString());
    }
    assertTrue(database.dropDatabase());
  }
  
  
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void shouldGetDateFromInvoice(int number) {
    //given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    List<LocalDate> localDateList = invGen.setRandomDates(LocalDate.now(), invoices);
    Database database = provideImplementation();
    setIdAndSaveInvoice(invoices, database);
    List<LocalDate> localDateListFromInv = new ArrayList<>();
    List<Invoice> invoiceList = database.getAllInvoices();
    
    // when
    invoiceList.forEach(invoice -> localDateListFromInv.add(invoice.getDate()));
    
    // then
    assertEquals(localDateListFromInv, localDateList);
    assertTrue(database.dropDatabase());
  }
  
  @Test
  @Parameters({"1", "3", "7", "12", "33", "50"})
  public void shouldGetInvoicesByDate(int number) {
    //given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    List<LocalDate> localDateList = invGen.setRandomDates(LocalDate.now(), invoices);
    Database database = provideImplementation();
    setIdAndSaveInvoice(invoices, database);
    
    LocalDate oldestDate = Collections.min(localDateList);
    LocalDate latestDate = Collections.max(localDateList);
    
    
    // when
    List<Invoice> shouldGetAll = database.getInvoicesFromDateToDate(oldestDate, latestDate);
    List<Invoice> shouldGetNone = database.getInvoicesFromDateToDate(latestDate, oldestDate);
    
    // then
    assertEquals(database.getAllInvoices().toString(), shouldGetAll.toString());
    if (!oldestDate.equals(latestDate)) {
      assertEquals(new ArrayList<Invoice>(), shouldGetNone);
    }
    assertTrue(database.dropDatabase());
  }
  
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void shouldGiveIdSaveAndReturnAllInvoices(int number) {
    // given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    Database database = provideImplementation();
    
    setIdAndSaveInvoice(invoices, database);
    
    // when
    List<Invoice> invoicesAll = database.getAllInvoices();
    
    // then
    assertEquals(invoices.size(), invoicesAll.size());
    assertEquals(invoices.toString(), invoicesAll.toString());
    assertTrue(database.dropDatabase());
  
  }
  
  /**
   * removing-one-adding-removed - looping through all
   **/
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void shouldRemoveInvoiceByIdIn(int number) {
    // given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    Database database = provideImplementation();
    
    setIdAndSaveInvoice(invoices, database);
    
    for (int id = 1; id < number; id++) {
      // when
      Invoice toBeRemovedInvoice = database.getInvoiceById(id);
      Invoice removedInvoice = database.removeInvoiceById(id);
      Invoice shouldBeNull = database.getInvoiceById(id);
      database.saveInvoice(removedInvoice);
  
      // then
      assertEquals(toBeRemovedInvoice.toString(), removedInvoice.toString());
      assertNull(shouldBeNull);
      assertEquals(database.getAllInvoices().size(), number);
    }
    assertTrue(database.dropDatabase());
  }
  
  /**
   * removing one-by-one until is empty
   **/
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void shouldRemoveAllById(int number) {
    // given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    Database database = provideImplementation();
    
    setIdAndSaveInvoice(invoices, database);
    
    for (int id = 1; id < number; id++) {
      // when
      Invoice toBeRemovedInvoice = database.getInvoiceById(id);
      Invoice removedInvoice = database.removeInvoiceById(id);
      
      // then
      assertNull(database.getInvoiceById(id));
      assertEquals(database.getAllInvoices().size(), number - id);
      assertEquals(toBeRemovedInvoice.toString(), removedInvoice.toString());
    }
    assertTrue(database.dropDatabase());
  }
  
  @Test
  @Parameters({"0", "1", "3", "7", "12", "33"})
  public void removeAllInvoices(int number) { //TODO
    //given
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    Database database = provideImplementation();

    setIdAndSaveInvoice(invoices, database);

    //when
    List<Invoice> removedInvoices = database.removeAllInvoices();

    //then
    assertEquals(invoices.toString(),removedInvoices.toString());
    assertEquals(0,database.getAllInvoices().size());
//    assertTrue(database.dropDatabase());
  }
  
//  @Test
//  public void dropDatabase() {
//    // given
//    Database database = provideImplementation();
//    // when
//    boolean result = database.dropDatabase();
//
//    // then
//    assertTrue(result);
//    assertTrue(database.getAllInvoices().isEmpty() || database.getAllInvoices() == null);
//  }
  
  /**
   * Supporting methods
   */
  private void setIdAndSaveInvoice(List<Invoice> invoices, Database database) {
    for (int loop = 0; loop < invoices.size(); loop++) {
      Invoice invoice = invoices.get(loop);
      invoice.setInvoiceId(database.getNextInvoiceId());
      database.saveInvoice(invoice);
    }
  }
}