package pl.coderstrust.logic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.coderstrust.db.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnitParamsRunner.class)
public class InvoiceBookTest {
  
  
  /**
   * Invoice in tests should be a mock?
   */
  private boolean invoiceIsMock = false;
  
  /**
   * supporting method - generate List<Invoice> of random #
   * of elements from 0 to number.
   *
   * @param number - max number of elements
   * @return - list of mock or null Invoices
   */
  private static List<Invoice> generateListOfNInvoices(int number, boolean isMock) {
    Invoice invoice = getInvoice(isMock);
    List<Invoice> invoices = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      invoices.add(invoice);
    }
    return invoices;
  }
  
  private static Invoice getInvoice(boolean isMock) {
    if (isMock) {
      return mock(Invoice.class);
    } else {
      return new Invoice();
    }
  }
  
  
  @Test
  public static void shouldUseDefaultConstructor() {
    //    given
    Object instanceGivenByDefaultConstructor = new InvoiceBook(mock(Database.class));
    Class expectedObjectName = instanceGivenByDefaultConstructor.getClass();
    System.out.println(expectedObjectName);
    
    //    when
    String resultObjectName = "class pl.coderstrust.logic.InvoiceBook";
    
    //    then
    assertEquals(expectedObjectName.toString(), resultObjectName);
  }
  
  
  @Test
  public void shouldHitSaveInvoice() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice invoice = getInvoice(invoiceIsMock);
    //    when
    invoiceBook.saveInvoice(invoice);
    //    then
    verify(database).getNextInvoiceId();
    verify(database).saveInvoice(invoice);
  }
  


  @Test
  @Parameters ({"0","10"})
  public void shouldUseSaveNInvoices(int number) {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    
    List<Invoice> invoices = generateListOfNInvoices(number, invoiceIsMock);
    
    //    when
    invoiceBook.saveInvoices(invoices);
  
    //    then
    for(Invoice invoice:invoices){
      verify(database, times(invoices.size())).getNextInvoiceId();
      verify(database, times(invoices.size())).saveInvoice(invoice);
    }
  }
  
  @Test
  public void shouldGetInvoiceById() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice expectedInvoice = getInvoice(invoiceIsMock);
  
    Integer nonExistingInvoiceId = 0;
    Integer existingInvoiceId = 1 + (int) (Math.random() * 10);
    
    when(database.getInvoice(existingInvoiceId)).thenReturn(expectedInvoice);
    when(database.getInvoice(nonExistingInvoiceId)).thenReturn(null);
  
    //    when
    Invoice actualInvoiceWhenExists = invoiceBook.getInvoice(existingInvoiceId);
    Invoice actualInvoiceWhenNotExists = invoiceBook.getInvoice(nonExistingInvoiceId);
  
    //    then
    assertEquals(expectedInvoice, actualInvoiceWhenExists);
    verify(database, times(1)).getInvoice(existingInvoiceId);
    assertNull(actualInvoiceWhenNotExists);
    verify(database, times(1)).getInvoice(nonExistingInvoiceId);
  }
  
  @Test
  public void shouldGetAllInvoices() {
  
    //given
    final Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    List<Invoice> expectedListOfInvoices = generateListOfNInvoices(20,
        invoiceIsMock);
    when(database.getAllInvoices()).thenReturn(expectedListOfInvoices);
  
    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getAllInvoices();
  
    //then
    assertEquals(expectedListOfInvoices, actualListOfInvoices);
  }

  @Test
  public void shouldRemoveInvoice() {
    //given
    int nonExistingInvoiceId0 = 0;
    int existingInvoiceId1 = 1;
  
    Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    when(database.removeInvoice(nonExistingInvoiceId0)).thenReturn(false);
    when(database.removeInvoice(existingInvoiceId1)).thenReturn(true);
  
    //when
    boolean expectedFalse = invoiceBook.removeInvoice(nonExistingInvoiceId0);
    boolean expectedTrue = invoiceBook.removeInvoice(existingInvoiceId1);
  
    //then
    assertFalse(expectedFalse);
    assertTrue(expectedTrue);
  
    verify(database).removeInvoice(nonExistingInvoiceId0);
    verify(database).removeInvoice(existingInvoiceId1);
  }
  
  
  @Test
  public void shouldRemoveListOfInvoiceById() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Integer> invoiceIdList = new ArrayList<>();
    invoiceIdList.add(0);
    invoiceIdList.add(1);
  
    when(database.removeInvoice(0)).thenReturn(false);
    when(database.removeInvoice(1)).thenReturn(true);
  
    boolean[] expectedResult = {false,true};
  
    //when
    boolean[] actualResult = invoiceBook.removeInvoicesById(invoiceIdList);
  
    //then
    assertArrayEquals(expectedResult, actualResult);
    assertEquals(2, actualResult.length);
    assertFalse(actualResult[0]);
    assertTrue(actualResult[1]);
    verify(database).removeInvoice(0);
    verify(database).removeInvoice(1);
  }
  
  @Test
  public void shouldGetListOfInvoiceById() {
    //given
    final Database database = mock(Database.class);
    final Invoice invoice = getInvoice(invoiceIsMock);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Integer> invoiceIdList = new ArrayList<>();
    invoiceIdList.add(0);
    invoiceIdList.add(1);
    
    List<Invoice> expectedListOfInvoices = new ArrayList<>();
    expectedListOfInvoices.add(null);
    expectedListOfInvoices.add(invoice);
    
    when(database.getInvoice(0)).thenReturn(null);
    when(database.getInvoice(1)).thenReturn(invoice);
    
    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getListOfInvoiceById(invoiceIdList);
    
    //then
    assertEquals(expectedListOfInvoices,actualListOfInvoices);
    assertEquals(2,actualListOfInvoices.size());
    assertEquals(invoice,actualListOfInvoices.get(1));
    assertNull(actualListOfInvoices.get(0));
    verify(database).getInvoice(0);
    verify(database).getInvoice(1);
  }
  
  @Test
  public void shouldUseToString() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    String expectedToString = InvoiceBook.class.getSimpleName() + "DatabaseToString";
    
    when(database.toString()).thenReturn("DatabaseToString");
    
    //when
    String actualToString = invoiceBook.toString();
    
    //then
    assertEquals(expectedToString, actualToString);
  }
  
  @Test
  public void shouldTestEquals() {
    //given
    Database database = mock(Database.class);
    Database database2 = mock(Database.class);
    Object object = mock(Object.class);
    
    InvoiceBook invoiceBook = new InvoiceBook(database);
    InvoiceBook invoiceBook1 = new InvoiceBook(database);
    InvoiceBook invoiceBook2 = new InvoiceBook(database2);
    
    //when
    boolean equals1 = invoiceBook.equals(invoiceBook1);
    boolean equals2 = invoiceBook.equals(invoiceBook2);
    boolean equals3 = invoiceBook.equals(object);
    boolean equals4 = invoiceBook.equals(invoiceBook);
    
    //then
    assertTrue(equals1);
    assertTrue(equals4);
    assertFalse(equals2);
    assertFalse(equals3);
    
  }
  
  @Test
  public void shouldTestHashcode() {
    //given
    Database database = mock(Database.class);
    Database database1 = mock(Database.class);
    
    InvoiceBook invoiceBook = new InvoiceBook(database);
    InvoiceBook invoiceBook1 = new InvoiceBook(database1);
    
    int databaseHashcode = database.hashCode();
    int database1Hashcode = database1.hashCode();
    
    //when
    int invoiceBookHashCode = invoiceBook.hashCode();
    int invoiceBook1HashCode = invoiceBook1.hashCode();
    
    //then
    assertEquals(databaseHashcode, invoiceBookHashCode);
    assertEquals(database1Hashcode, invoiceBook1HashCode);
    assertFalse(invoiceBookHashCode == invoiceBook1HashCode);
    assertFalse(databaseHashcode == database1Hashcode);
    
  }
}