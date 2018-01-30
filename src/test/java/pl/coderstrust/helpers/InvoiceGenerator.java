package pl.coderstrust.helpers;

import static org.mockito.Mockito.mock;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.Item;
import pl.coderstrust.model.Vat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGenerator {
  

  /**
   * supporting method - generate List of Invoices with given number of elements.
   *
   * @param number - max number of elements
   * @return - list of mock or null Invoices
   */
  public List<Invoice> generateListOfNInvoices(int number, boolean invoiceIsMock) {
    List<Invoice> invoices = new ArrayList<>();  
    for (int i = 0; i < number; i++) {
      Invoice invoice = getInvoice(invoiceIsMock);
      invoices.add(invoice);

    }
    return invoices;
  }
  
  public List<LocalDate> setRandomDates(LocalDate date, List<Invoice> invoiceList) {
    LocalDate tmpDate;
    List<LocalDate> localDates = new ArrayList<>();
    for (Invoice invoice : invoiceList) {
      int randomBase = (int) (Math.random() * 365);
      if (randomBase % 3 == 0) {
        tmpDate = date.minusDays(randomBase);
        localDates.add(tmpDate);
        invoice.setDate(tmpDate);
      } else if (randomBase % 3 == 1) {
        tmpDate = date.minusWeeks(randomBase);
        localDates.add(tmpDate);
        invoice.setDate(tmpDate);
      } else if (randomBase % 3 == 2) {
        tmpDate = date.minusMonths(randomBase);
        localDates.add(tmpDate);
        invoice.setDate(tmpDate);
      } else {
        return null;
      }
    }
    return localDates;
  }
  

  public Invoice getInvoice(boolean invoiceIsMock) {
    if (invoiceIsMock) {
      return mock(Invoice.class);
    } else {
      Invoice invoice = new Invoice();
      invoice.setDate(LocalDate.now());
      invoice.setBuyer(new Company("Buyer", "123"));
      invoice.setSeller(new Company("Seller", "456"));
      invoice.setItems(getItems());
      return invoice;
    }
  }
  
  private List<Item> getItems() {
    Item item1 = new Item("Gold", 100., Vat.vatFree);
    Item item2 = new Item("Wood", 5., Vat.vat5);
    Item item3 = new Item("Stone", 10., Vat.vat8);
    Item item4 = new Item("Sulfur", 25., Vat.vat23);
    List<Item> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    items.add(item4);
    return items;
  }
  
}
