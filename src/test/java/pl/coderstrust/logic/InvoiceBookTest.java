package pl.coderstrust.logic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junitparams.JUnitParamsRunner;
import org.junit.Assert;
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
  private boolean invoiceIsMock = true;

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


  /**
   * supporting method - generate List<Invoice> of random # of elements from 0 to n.
   *
   * @param n - max number of elements
   * @return - list of mock or null Invoices
   */
  private static List<Invoice> generateListOf0toNInvoices(int n, boolean isMock) {
    Invoice invoice = getInvoice(isMock);
    List<Invoice> invoices = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      invoices.add(invoice);
    }
    return invoices;
  }

  private static Invoice getInvoice(boolean isMock) {
    Invoice invoice;
    if (isMock) {
      return invoice = mock(Invoice.class);
    } else {
      return invoice = new Invoice();
    }
  }

  @Test
  public void shouldHitSaveInvoice() throws Exception {
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

  /**
   * This method is only tested to use saveInvoice a given number of times. For randomly generated
   * values add "Description" in assert.
   */
  @Test
  public void shouldUseSave0Invoices() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);

    List<Invoice> invoices = generateListOf0toNInvoices(10, invoiceIsMock);

    //    when
    invoiceBook.saveInvoices(invoices);
    //    then
    for (Invoice invoice : invoices) {
      verify(database, times(invoices.size())).getNextInvoiceId();
      verify(database, times(invoices.size())).saveInvoice(invoice);
    }


  }

  @Test
  public void shouldUseSave10Invoices() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);

    List<Invoice> invoices = generateListOf0toNInvoices(10, invoiceIsMock);

    //    when
    invoiceBook.saveInvoices(invoices);
    //    then
    for (Invoice invoice : invoices) {
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
    Assert.assertEquals(expectedInvoice, actualInvoiceWhenExists);
    verify(database, times(1)).getInvoice(existingInvoiceId);
    Assert.assertEquals(null, actualInvoiceWhenNotExists);
    verify(database, times(1)).getInvoice(nonExistingInvoiceId);
  }

  @Test
  public void shouldGetAllInvoices() {

    //given
    final Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    List<Invoice> expectedListOfInvoices = generateListOf0toNInvoices(20,
        invoiceIsMock);
    when(database.getAllInvoices()).thenReturn(expectedListOfInvoices);

    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getAllInvoices();

    //then
    assertEquals(expectedListOfInvoices, actualListOfInvoices);
  }

  @Test
  public void shouldRemoveInvoice() throws Exception {
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
    assertEquals(false, expectedFalse);
    assertEquals(true, expectedTrue);

    verify(database).removeInvoice(nonExistingInvoiceId0);
    verify(database).removeInvoice(existingInvoiceId1);
  }


  @Test
  public void shouldRemoveListOfInvoiceById() throws Exception {
    //given
    final Database database = mock(Database.class);
    final Invoice invoice = getInvoice(invoiceIsMock);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final List<Integer> invoiceIdList = new ArrayList<>();
    invoiceIdList.add(0);
    invoiceIdList.add(1);

    boolean[] expectedResult = new boolean[2];
    expectedResult[0] = false;
    expectedResult[1] = true;

    when(database.removeInvoice(0)).thenReturn(false);
    when(database.removeInvoice(1)).thenReturn(true);

    //when
    boolean[] actualResult = invoiceBook.removeInvoicesById(invoiceIdList);

    //then
    assertArrayEquals(expectedResult, actualResult);
    assertEquals(2, actualResult.length);
    assertEquals(false, actualResult[0]);
    assertEquals(true, actualResult[1]);
    verify(database).removeInvoice(0);
    verify(database).removeInvoice(1);
  }

  @Test
  public void shouldGetListOfInvoiceById() throws Exception {
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
    assertEquals(expectedListOfInvoices, actualListOfInvoices);
    assertEquals(2, actualListOfInvoices.size());
    assertEquals(invoice, actualListOfInvoices.get(1));
    assertEquals(null, actualListOfInvoices.get(0));
    verify(database).getInvoice(0);
    verify(database).getInvoice(1);
  }
}