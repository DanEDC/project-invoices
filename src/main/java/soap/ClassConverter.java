package soap;

import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Items;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.coderstrust.model.Company;

public class ClassConverter {

  public static Function<Company, io.spring.guides.gs_producing_web_service.Company> convertCompanyToSoap =
      new Function<Company, io.spring.guides.gs_producing_web_service.Company>() {
        @Override
        public io.spring.guides.gs_producing_web_service.Company apply(Company company) {
          io.spring.guides.gs_producing_web_service.Company generatedCompany = new io.spring.guides.gs_producing_web_service.Company();
          generatedCompany.setName(company.getName());
          generatedCompany.setVatID(company.getVatId());
          return generatedCompany;
        }
      };

  public static Function<pl.coderstrust.model.Invoice, Invoice> convertInvoicToSoap =
      new Function<pl.coderstrust.model.Invoice, Invoice>() {
    @Override
    public Invoice apply(pl.coderstrust.model.Invoice invoice) {
      Invoice generatedInvoice = new Invoice();
      generatedInvoice.setInvoiceID(BigInteger.valueOf(invoice.getInvoiceId()));
      try {
        generatedInvoice.setDate(ConvertedDate(invoice.getDate()));
        generatedInvoice.setSeller(convertCompanyToSoap.apply(invoice.getSeller()));
        generatedInvoice.setBuyer(convertCompanyToSoap.apply(invoice.getBuyer()));
        generatedInvoice.getItems().addAll(
            invoice.getItems()
                .stream()
                .map(ClassConverter::convertedItems)
                .collect(Collectors.toList()));
        return generatedInvoice;
      } catch (DatatypeConfigurationException e) {
        e.printStackTrace();
        return null;
      }
    }
  };


    public static Items convertedItems(pl.coderstrust.model.Item item) {
      Items generatedItems = new Items();
      generatedItems.setItemID(BigInteger.valueOf(item.getItemId()));
      generatedItems.setDescription(item.getDescription());
      generatedItems.setValue(item.getValue());
      generatedItems.setVat(item.getVat().getVat());
      return generatedItems;
    }



  private static XMLGregorianCalendar ConvertedDate(LocalDate date) throws DatatypeConfigurationException {
    return DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(date.toString());
  }
}


