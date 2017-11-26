package pl.coderstrust.model;

import pl.coderstrust.logic.InvoiceBook;

import java.time.LocalDate;

public class Main {
  
  public static void main(String[] args) {
    
    InvoiceBook invoices2017 = new InvoiceBook();
    Company myself = new Company("MateDev", "123456789");
    Company favouriteComputerSeller = new Company("Optimus", "234543098");
    
    InvoiceItem[] items = {new InvoiceItem("DellXPS15", 10000, Vat.vat23)};
    
    invoices2017.addInvoice(
        new Invoice(LocalDate.now(),
            favouriteComputerSeller,
            myself, items, Vat.vat23));
    invoices2017.addInvoice(
        new Invoice(LocalDate.now(),
            favouriteComputerSeller,
            myself, items, Vat.vat23));
    
    System.out.println(invoices2017);
    
  }
}
