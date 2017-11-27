package pl.coderstrust.model;

import pl.coderstrust.logic.InvoiceBook;

import java.time.LocalDate;

public class Main {
  
  public static void main(String[] args) {
    
    InvoiceBook invoices2017 = new InvoiceBook();
    Company myself = new Company("MateDev", "123456789");
    Company favouriteComputerSeller = new Company("Optimus", "234543098");
    
    Item[] items = {new Item("DellXPS15", 10000, Vat.vat23),new Item("DellXPS15", 10000, Vat.vat23)};
    
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
    
    invoices2017.removeInvoice(i1);
    System.out.println(invoices2017);
  

    
  }
}

