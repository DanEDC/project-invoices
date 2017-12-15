package pl.coderstrust.logic;

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
import java.util.Collection;
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
    for (int i = 0; i < (int) (Math.random() * n); i++) {
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
  
  /**
   * This method is only tested to use saveInvoice a given number of times.
   */
  @Test
  public void saveInvoices() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice invoice = getInvoice(invoiceIsMock);
    List<Invoice> invoices = generateListOf0toNInvoices(10, invoiceIsMock);
    
    //    when
    invoiceBook.saveInvoices(invoices);
    //    then
    verify(database, times(invoices.size())).getNextInvoiceId();
    verify(database, times(invoices.size())).saveInvoice(invoice);
  }
  
  @Test
  public void getInvoice() {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice expectedInvoice = getInvoice(invoiceIsMock);
    Integer existingInvoiceId = (int) (Math.random() * 10);
    when(database.getInvoice(existingInvoiceId)).thenReturn(expectedInvoice);
  
    //    when
    Invoice actualInvoice = invoiceBook.getInvoice(existingInvoiceId);
  
    //    then
    Assert.assertEquals(expectedInvoice, actualInvoice);
    verify(database).getInvoice(existingInvoiceId);
  }
  
  @Test
  public void getAllInvoices() {
  
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
  public void removeInvoice() throws Exception {
    //given
    final Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    
    //when
    database.removeInvoice()
    
  }

//  @Test
//  public void removeInvoices() throws Exception {
//  }
  
  @Test
  public void saveInvoice() throws Exception {
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
  public void getListOfInvoiceById(Collection<Integer> orderedInvoicesId) {
    //given
    final Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    List<Invoice> expectedListOfInvoices = generateListOf0toNInvoices(20, invoiceIsMock);
    
    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getAllInvoices();
    
    //then
    assertEquals(expectedListOfInvoices, actualListOfInvoices);
  }
}