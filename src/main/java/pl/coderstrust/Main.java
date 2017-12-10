package pl.coderstrust;

import pl.coderstrust.db.impl.memory.InMemory;
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
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(3);
    list.add(4);
    List<Integer> list1 = new ArrayList<>();
    list.add(5);
    list.add(6);
    list.add(7);
  
    InvoiceBook ib = new InvoiceBook(new InMemory());
  
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
  
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(i2);
    invoices.add(i3);
    invoices.add(i4);
  
    ib.saveInvoice(i1);
    System.out.println(ib);
    ib.saveInvoices(invoices);
    System.out.println(ib);
    ib.removeInvoice(2);
    System.out.println(ib);
    System.out.println("");
    System.out.println(ib.getInvoice(2));
    System.out.println(ib);
    System.out.println("");
    System.out.println("Rem invoices:" + list);
    ib.removeInvoices(list);
    System.out.println("");
    System.out.println(ib);
    ib.saveInvoices(invoices);
    System.out.println(ib);
    System.out.println("");
    System.out.println(ib.getInvoices(list1));
    
  }
}

