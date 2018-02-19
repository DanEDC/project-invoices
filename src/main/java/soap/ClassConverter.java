package soap;

import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Items;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.coderstrust.model.Company;

import java.time.LocalDate;
import java.util.function.Function;

public class ClassConverter {

  Function<Company, io.spring.guides.gs_producing_web_service.Company> convertedCompany = new Function<Company, io.spring.guides.gs_producing_web_service.Company>() {
    @Override
    public io.spring.guides.gs_producing_web_service.Company apply(Company company) {
      io.spring.guides.gs_producing_web_service.Company generatedCompany = new io.spring.guides.gs_producing_web_service.Company();
      generatedCompany.setName(company.getName());
      generatedCompany.setVatID(company.getVatId());
      return generatedCompany;
    }

    Function<pl.coderstrust.model.Invoice, Invoice> convertedInvoice = new Function<pl.coderstrust.model.Invoice, Invoice>() {
      @Override
      public Invoice apply(pl.coderstrust.model.Invoice invoice) {
        Invoice generatedInvoice = new Invoice();
        generatedInvoice.setInvoiceID(invoice.getInvoiceId());
        generatedInvoice.setDate(ConvertedDate(invoice.getDate()));
        generatedInvoice.setSeller(invoice.getSeller());
        generatedInvoice.setBuyer(invoice.getBuyer());
        generatedInvoice.setItems(invoice.getItems();
        return generatedInvoice;
      }
    };
    Function<pl.coderstrust.model.Item, Items> convertedItems = new Function<pl.coderstrust.model.Item, Items>() {
      @Override
      public Items apply(pl.coderstrust.model.Item item) {
        Items generatedItems = new Items();
        generatedItems.setItemID(item.getItemId());
        generatedItems.setDescription(item.getDescription());
        generatedItems.setValue(item.getValue());
        generatedItems.setVat(item.getVat());
        return generatedItems;
      }
    };

    private XMLGregorianCalendar xcal;

    private XMLGregorianCalendar ConvertedDate(LocalDate date) {
      try {
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance()
            .newXMLGregorianCalendar(date.toString());
      } catch (DatatypeConfigurationException e) {
        e.printStackTrace();
      }

      return xcal;
    }
  }
}
}


