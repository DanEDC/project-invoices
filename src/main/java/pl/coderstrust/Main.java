package pl.coderstrust;

import pl.coderstrust.db.Database;
import pl.coderstrust.db.impl.file.InFile;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.Item;
import pl.coderstrust.model.Vat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
  
  public static void main(String[] args) {
  
//    List<Integer> list1 = new ArrayList<>();
//    list1.add(5);
//    list1.add(6);
//    list1.add(10);
//    list1.add(0);
  
    InvoiceBook database = new InvoiceBook(new InFile());
  
    testSave(database);
    testSave(database);
//    System.out.println(database.getAllInvoices());
//    System.out.println(Arrays.toString(database.removeInvoices(list1)));
//    System.out.println(database.getAllInvoices());
//    System.out.println(database.getAllInvoices());
//    testRead(database);
  
  
  }
  
  private static void testRead(InvoiceBook database) {
    System.out.println(database.getInvoice(1));
    
  }
  
  private static void testSave(InvoiceBook ib) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(4);
    List<Integer> list1 = new ArrayList<>();
    list.add(5);
    list.add(6);
    
    
    System.out.println(ib);
    Company Mat = new Company("MD", "123");
    Company Sel = new Company("Op", "987");
    
    Item item1 = new Item("Wood", 5, Vat.vat5);
    Item item2 = new Item("Stone", 5, Vat.vat8);
    Item item3 = new Item("Gem", 20, Vat.vatFree);
    
    List<Item> items1 = new ArrayList<>();
    items1.add(item1);
    List<Item> items2 = new ArrayList<>();
    items2.add(item2);
    List<Item> items3 = new ArrayList<>();
    items3.add(item3);
    items3.add(item3);
    items3.add(item3);
    
    Invoice i1 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i2 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i3 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i4 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i5 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i6 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i7 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i8 = new Invoice(LocalDate.now(), Sel, Mat, items3);
  
    List<Invoice> invoices1 = new ArrayList<>();
    invoices1.add(i2);
    invoices1.add(i3);
    invoices1.add(i4);
    List<Invoice> invoices2 = new ArrayList<>();
    invoices2.add(i5);
    invoices2.add(i6);
    invoices2.add(i7);
    List<Invoice> invoices3 = new ArrayList<>();
    invoices2.add(i8);
    
    ib.saveInvoice(i1);
    ib.saveInvoices(invoices1);
    ib.saveInvoices(invoices2);
    ib.saveInvoices(invoices3);
  }
  
  private static void testSaveGet(InvoiceBook ib) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(4);
    List<Integer> list1 = new ArrayList<>();
    list.add(5);
    list.add(6);
    
    
    System.out.println(ib);
    Company Mat = new Company("MD", "123");
    Company Sel = new Company("Op", "987");
    
    Item item1 = new Item("Wood", 5, Vat.vat5);
    Item item2 = new Item("Stone", 5, Vat.vat8);
    Item item3 = new Item("Gem", 20, Vat.vatFree);
    
    List<Item> items1 = new ArrayList<>();
    items1.add(item1);
    List<Item> items2 = new ArrayList<>();
    items2.add(item2);
    List<Item> items3 = new ArrayList<>();
    items3.add(item3);
    
    Invoice i1 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i2 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i3 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i4 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i5 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i6 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i7 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i8 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i9 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    
    List<Invoice> invoices1 = new ArrayList<>();
    invoices1.add(i2);
    invoices1.add(i3);
    invoices1.add(i4);
    List<Invoice> invoices2 = new ArrayList<>();
    invoices2.add(i5);
    invoices2.add(i6);
    invoices2.add(i7);
    List<Invoice> invoices3 = new ArrayList<>();
    invoices2.add(i8);
    
    ib.saveInvoice(i1);
    System.out.println(ib);
    ib.saveInvoices(invoices1);
    System.out.println(ib);
    System.out.println("");
    System.out.println(ib.getInvoice(2));
    System.out.println("");
    System.out.println(ib);
    System.out.println("");
    ib.saveInvoices(invoices2);
    System.out.println("");
    System.out.println(ib);
    System.out.println("");
    ib.getAllInvoices();
    System.out.println("");
    System.out.println(ib);
    System.out.println("\n3\n");
    ib.saveInvoices(invoices3);
    System.out.println(ib);
    System.out.println("\n4\n");
    System.out.println(ib.getAllInvoices());
  }
  
  
  private static void testSaveGetRemove(Database database) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(4);
    List<Integer> list1 = new ArrayList<>();
    list.add(5);
    list.add(6);
    
    InvoiceBook ib = new InvoiceBook(database);
    
    System.out.println(ib);
    Company Mat = new Company("MD", "123");
    Company Sel = new Company("Op", "987");
    
    Item item1 = new Item("Wood", 5, Vat.vat5);
    Item item2 = new Item("Stone", 5, Vat.vat8);
    Item item3 = new Item("Gem", 20, Vat.vatFree);
    
    List<Item> items1 = new ArrayList<>();
    items1.add(item1);
    List<Item> items2 = new ArrayList<>();
    items2.add(item2);
    List<Item> items3 = new ArrayList<>();
    items3.add(item3);
    
    Invoice i1 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i2 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i3 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i4 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i5 = new Invoice(LocalDate.now(), Sel, Mat, items2);
    Invoice i6 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i7 = new Invoice(LocalDate.now(), Sel, Mat, items1);
    Invoice i8 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    Invoice i9 = new Invoice(LocalDate.now(), Sel, Mat, items3);
    
    List<Invoice> invoices1 = new ArrayList<>();
    invoices1.add(i2);
    invoices1.add(i3);
    invoices1.add(i4);
    List<Invoice> invoices2 = new ArrayList<>();
    invoices2.add(i5);
    invoices2.add(i6);
    invoices2.add(i7);
    List<Invoice> invoices3 = new ArrayList<>();
    invoices2.add(i8);
  
  
    ib.saveInvoice(i1);
    System.out.println(ib);
    ib.saveInvoices(invoices1);
    System.out.println(ib);
    ib.removeInvoice(2);
    System.out.println(ib);
    System.out.println("");
    System.out.println(ib.getInvoice(2));
    System.out.println(ib);
    System.out.println("");
    System.out.println("Rem invoices1:" + list);
    ib.removeInvoices(list);
    System.out.println("");
    System.out.println(ib);
    ib.saveInvoices(invoices2);
    System.out.println(ib);
    System.out.println("");
    ib.removeInvoices(list);
    System.out.println("\n1\n");
    System.out.println(ib);
    System.out.println("\n2\n");
    ib.getAllInvoices();
    System.out.println(ib);
    System.out.println("\n3\n");
    ib.saveInvoices(invoices3);
    System.out.println(ib);
    System.out.println("\n4\n");
    System.out.println(ib.getAllInvoices());
  }
}

