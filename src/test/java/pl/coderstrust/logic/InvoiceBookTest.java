package pl.coderstrust.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.coderstrust.db.Database;
import pl.coderstrust.helpers.InvoiceGenerator;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnitParamsRunner.class)
public class InvoiceBookTest {
  
  private InvoiceGenerator invGen = new InvoiceGenerator();
  
  /**
   * Invoice in tests should be a mock?
   */
  private boolean invoiceIsMock = true;
  
  @Test
  public static void shouldUseDefaultConstructor() {
    //given
    Object instanceGivenByDefaultConstructor = new InvoiceBook(mock(Database.class));
    Class expectedObjectName = instanceGivenByDefaultConstructor.getClass();
    System.out.println(expectedObjectName);
  
    //when
    String resultObjectName = "class pl.coderstrust.logic.InvoiceBook";
  
    //then
    assertEquals(expectedObjectName.toString(), resultObjectName);
  }
  
  
  @Test
  public void shouldHitSaveInvoice() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice invoice = invGen.getInvoice(invoiceIsMock);
  
    //when
    invoiceBook.saveInvoice(invoice);
  
    //then
    verify(database).getNextInvoiceId();
    verify(database).saveInvoice(invoice);
  }
  


  @Test
  @Parameters ({"0","1","3","12","33","33"})
  public void shouldUseSaveNInvoices(int number) {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    
    List<Invoice> invoices = invGen.generateListOfNInvoices(number, invoiceIsMock);
    when(database.saveInvoice(any(Invoice.class))).thenReturn(true);
  
    //when
    invoiceBook.saveInvoices(invoices);
  
    //then
    verify(database, times(invoices.size())).getNextInvoiceId();
    verify(database, times(invoices.size())).saveInvoice(any(Invoice.class));
  }
  
  @Test
  public void shouldGetInvoiceById() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice expectedInvoice = invGen.getInvoice(invoiceIsMock);
  
    Integer nonExistingInvoiceId = 0;
    Integer existingInvoiceId = 1 + (int) (Math.random() * 10);
    
    when(database.getInvoiceById(existingInvoiceId)).thenReturn(expectedInvoice);
    when(database.getInvoiceById(nonExistingInvoiceId)).thenReturn(null);
  
    //when
    Invoice actualInvoiceWhenExists = invoiceBook.getInvoiceById(existingInvoiceId);
    Invoice actualInvoiceWhenNotExists = invoiceBook.getInvoiceById(nonExistingInvoiceId);
  
    //then
    assertEquals(expectedInvoice, actualInvoiceWhenExists);
    verify(database, times(1)).getInvoiceById(existingInvoiceId);
    assertNull(actualInvoiceWhenNotExists);
    verify(database, times(1)).getInvoiceById(nonExistingInvoiceId);
  }
  
  @Test
  public void shouldGetAllInvoices() {
  
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Invoice> expectedListOfInvoices = invGen.generateListOfNInvoices(20,
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
    final int nonExistingInvoiceId0 = 0;
    final int existingInvoiceId1 = 1;
    Invoice invoice = invGen.getInvoice(invoiceIsMock);
  
    Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    when(database.removeInvoiceById(nonExistingInvoiceId0)).thenReturn(invoice);
    when(database.removeInvoiceById(existingInvoiceId1)).thenReturn(null);
  
    //when
    Invoice expectedInvoice = invoiceBook.removeInvoice(nonExistingInvoiceId0);
    Invoice expectedNull = invoiceBook.removeInvoice(existingInvoiceId1);
  
    //then
    assertEquals(invoice, expectedInvoice);
    assertNull(expectedNull);
  
    verify(database).removeInvoiceById(nonExistingInvoiceId0);
    verify(database).removeInvoiceById(existingInvoiceId1);
  }
  
  
  @Test
  public void shouldRemoveListOfInvoiceById() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Integer> invoiceIdList = new ArrayList<>();
    invoiceIdList.add(0);
    invoiceIdList.add(1);
    Invoice invoice = invGen.getInvoice(invoiceIsMock);
  
    when(database.removeInvoiceById(0)).thenReturn(null);
    when(database.removeInvoiceById(1)).thenReturn(invoice);
  
    List<Invoice> expectedResult = new ArrayList<>();
    expectedResult.add(0, null);
    expectedResult.add(1, invoice);
  
    //when
    List<Invoice> actualResult = invoiceBook.removeInvoicesById(invoiceIdList);
  
    //then
    assertEquals(expectedResult, actualResult);
    assertEquals(2, actualResult.size());
    verify(database).removeInvoiceById(0);
    verify(database).removeInvoiceById(1);
  }
  
  @Test
  public void shouldGetListOfInvoiceById() {
    //given
    final Database database = mock(Database.class);
    final Invoice invoice = invGen.getInvoice(invoiceIsMock);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Integer> invoiceIdList = new ArrayList<>();
    invoiceIdList.add(0);
    invoiceIdList.add(1);
    
    List<Invoice> expectedListOfInvoices = new ArrayList<>();
    expectedListOfInvoices.add(null);
    expectedListOfInvoices.add(invoice);
    
    when(database.getInvoiceById(0)).thenReturn(null);
    when(database.getInvoiceById(1)).thenReturn(invoice);
  
    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getListOfInvoiceById(invoiceIdList);
  
    //then
    assertEquals(expectedListOfInvoices,actualListOfInvoices);
    assertEquals(2,actualListOfInvoices.size());
    assertEquals(invoice,actualListOfInvoices.get(1));
    assertNull(actualListOfInvoices.get(0));
    verify(database).getInvoiceById(0);
    verify(database).getInvoiceById(1);
  }
  
  @Test
  public void shouldUseToString() {
    //given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final String expectedToString = InvoiceBook.class.getSimpleName() + "DatabaseToString";
    
    when(database.toString()).thenReturn("DatabaseToString");
  
    //when
    String actualToString = invoiceBook.toString();
  
    //then
    assertEquals(expectedToString, actualToString);
  }
  
  @Test
  public void shouldTestEquals() {
    //given
    final Database database = mock(Database.class);
    final Database database2 = mock(Database.class);
    final Object object = mock(Object.class);
    
    InvoiceBook invoiceBook = new InvoiceBook(database);
    InvoiceBook invoiceBook1 = new InvoiceBook(database);
    InvoiceBook invoiceBook2 = new InvoiceBook(database2);
  
    //when
    boolean equals1 = invoiceBook.equals(invoiceBook1);
    boolean equals2 = invoiceBook.equals(invoiceBook2);
    boolean equals3 = invoiceBook.equals(object);
  
    //then
    assertTrue(equals1);
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