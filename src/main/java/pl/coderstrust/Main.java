package pl.coderstrust;

import pl.coderstrust.db.impl.inMemory.InMemoryDB;
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
  
    InvoiceBook ib = new InvoiceBook(new InMemoryDB());
  
    System.out.println(ib);
    Company MtuDls = new Company("MateDev", "123456789");
    Company Optimus = new Company("Optimus", "234543098");
  
  
    Item item1 = new Item("DellXPS15", 10000, Vat.vat23);
    Item item2 = new Item("Monitor27\"", 5000, Vat.vat23);
    Item item3 = new Item("laptop bag", 200, Vat.vat23);
  
    List<Item> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
  
    Invoice i1 = new Invoice(LocalDate.now(), Optimus, MtuDls, items);
    Invoice i2 = new Invoice(LocalDate.now(), Optimus, MtuDls, items);
    Invoice i3 = new Invoice(LocalDate.now(), Optimus, MtuDls, items);
    Invoice i4 = new Invoice(LocalDate.now(), Optimus, MtuDls, items);
  
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(i2);
    invoices.add(i3);
    invoices.add(i4);
  
    ib.saveInvoice(i1);
    System.out.println(ib);
    ib.saveInvoices(invoices);
    System.out.println(ib);
    
  }
}

