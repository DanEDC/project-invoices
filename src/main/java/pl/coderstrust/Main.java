package pl.coderstrust;

import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.Item;
import pl.coderstrust.model.Vat;

import java.time.LocalDate;

public class Main {
  
  public static void main(String[] args) {
    
    InvoiceBook invoices2017 = new InvoiceBook();
    Company myself = new Company("MateDev", "123456789");
    Company favouriteComputerSeller = new Company("Optimus", "234543098");
  
  
    Item item1 = new Item("DellXPS15", 10000, Vat.vat23);
    Item item2 = new Item("Monitor27\"", 5000, Vat.vat23);
    Item item3 = new Item("laptop bag", 200, Vat.vat23);
    
    Item[] items = {item1,item2,item3};
    
    Invoice i1 = new Invoice(LocalDate.now(),
        favouriteComputerSeller,
        myself, items, Vat.vat23);
    Invoice i2 = new Invoice(LocalDate.now(),
        favouriteComputerSeller,
        myself, items, Vat.vat23);
  
    invoices2017.addInvoice(
        i1);
    invoices2017.addInvoice(
        i2);
    
    System.out.println(invoices2017);
  

    
  }
}

