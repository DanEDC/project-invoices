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
import java.util.List;


@RunWith(JUnitParamsRunner.class)
public class InvoiceBookTest {
  
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
  
  private static List<Invoice> generateListOf0toNMockInvoices(int n) {
    Invoice invoice = mock(Invoice.class);
    List<Invoice> invoices = new ArrayList<>();
    for (int i = 0; i < (int) (Math.random() * n); i++) {
      invoices.add(invoice);
    }
    return invoices;
  }
  
  @Test
  public void saveInvoices() throws Exception {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice invoice = mock(Invoice.class);
    List<Invoice> invoices = generateListOf0toNMockInvoices(10);
    
    //    when
    invoiceBook.saveInvoices(invoices);
    //    then
    verify(database, times(invoices.size())).getNextInvoiceId();
    verify(database, times(invoices.size())).saveInvoice(invoice);
  }
  
  @Test
  public void getInvoice() throws Exception {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice expectedInvoice = mock(Invoice.class);
    Integer invoiceId = (int) (Math.random() * 10);
    when(database.getInvoice(invoiceId)).thenReturn(expectedInvoice);
    
    //    when
    Invoice actualInvoice = invoiceBook.getInvoice(invoiceId);
    
    //    then
    Assert.assertEquals(expectedInvoice, actualInvoice);
    verify(database).getInvoice(invoiceId);
  }
  
  
  @Test
  public void getAllInvoices() throws Exception {
    
    //given
    final Database database = mock(Database.class);
    InvoiceBook invoiceBook = new InvoiceBook(database);
    List<Invoice> expectedListOfInvoices = generateListOf0toNMockInvoices(20);
    when(database.getAllInvoices()).thenReturn(expectedListOfInvoices);
    
    //when
    List<Invoice> actualListOfInvoices = invoiceBook.getAllInvoices();
    
    //then
    assertEquals(expectedListOfInvoices, actualListOfInvoices);
  }

  @Test
  public void getInvoices() throws Exception {
  }

//  @Test
//  public void removeInvoice() throws Exception {
//  }
//
//  @Test
//  public void removeInvoices() throws Exception {
//  }
  
  @Test
  public void saveInvoice() throws Exception {
    //    given
    final Database database = mock(Database.class);
    final InvoiceBook invoiceBook = new InvoiceBook(database);
    final Invoice invoice = mock(Invoice.class);
    //    when
    invoiceBook.saveInvoice(invoice);
    //    then
    verify(database).getNextInvoiceId();
    verify(database).saveInvoice(invoice);
  }
  
  
}