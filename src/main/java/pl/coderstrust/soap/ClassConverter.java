package pl.coderstrust.soap;

import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Items;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Item;
import pl.coderstrust.model.Vat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClassConverter {

  private static Function<Company, io.spring.guides.gs_producing_web_service.Company> convertFromCompanyToSoap =
      new Function<Company, io.spring.guides.gs_producing_web_service.Company>() {
        @Override
        public io.spring.guides.gs_producing_web_service.Company apply(Company company) {
          io.spring.guides.gs_producing_web_service.Company generatedCompany = new io.spring.guides.gs_producing_web_service.Company();
          generatedCompany.setName(company.getName());
          generatedCompany.setVatID(company.getVatId());
          return generatedCompany;
        }
      };

  public static Function<pl.coderstrust.model.Invoice, Invoice> convertFromInvoiceToSoap =
      new Function<pl.coderstrust.model.Invoice, Invoice>() {
        @Override
        public Invoice apply(pl.coderstrust.model.Invoice invoice) {
          Invoice generatedInvoice = new Invoice();
          generatedInvoice.setInvoiceID(BigInteger.valueOf(invoice.getInvoiceId()));
          try {
            generatedInvoice.setDate(convertFromDateToSoap(invoice.getDate()));
            generatedInvoice.setSeller(convertFromCompanyToSoap.apply(invoice.getSeller()));
            generatedInvoice.setBuyer(convertFromCompanyToSoap.apply(invoice.getBuyer()));
            generatedInvoice.getItems().addAll(
                invoice.getItems()
                    .stream()
                    .map(ClassConverter::convertFromItemToSoap)
                    .collect(Collectors.toList()));
            return generatedInvoice;
          } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
          }
        }
      };
  private static Function<io.spring.guides.gs_producing_web_service.Company, Company> convertFromSoapToCompany =
      new Function<io.spring.guides.gs_producing_web_service.Company, Company>() {
        @Override
        public Company apply(io.spring.guides.gs_producing_web_service.Company company) {
          Company modelCompany = new Company();
          modelCompany.setName(company.getName());
          modelCompany.setVatId(company.getVatID());
          return modelCompany;
        }
      };
  public static Function<Invoice, pl.coderstrust.model.Invoice> convertFromSoapToInvoice =
      new Function<Invoice, pl.coderstrust.model.Invoice>() {
        @Override
        public pl.coderstrust.model.Invoice apply(Invoice invoice) {
          pl.coderstrust.model.Invoice modelInvoice = new pl.coderstrust.model.Invoice();
          modelInvoice.setInvoiceId(invoice.getInvoiceID().intValue());
          modelInvoice.setDate(convertFromSoapToDate(invoice.getDate()));
          modelInvoice.setSeller(convertFromSoapToCompany.apply(invoice.getSeller()));
          modelInvoice.setBuyer(convertFromSoapToCompany.apply(invoice.getBuyer()));
          modelInvoice.getItems().addAll(
              invoice.getItems()
                  .stream()
                  .map(ClassConverter::convertFromSoapToItem)
                  .collect(Collectors.toList()));
          return modelInvoice;
        }
      };
  Function<Vat, Items> convertFromVatToSoap = new Function<Vat, Items>() {
    @Override
    public Items apply(Vat vat) {
      return null;
    }
  };

  private static Items convertFromItemToSoap(pl.coderstrust.model.Item item) {
    Items generatedItems = new Items();
    //generatedItems.setItemID(BigInteger.valueOf(item.getItemId()));
    generatedItems.setDescription(item.getDescription());
    generatedItems.setValue(item.getValue());
    //generatedItems.setVat(item.getVat().getVat());
    return generatedItems;
  }

  private static Item convertFromSoapToItem(Items items) {
    Item modelItem = new Item();
    modelItem.setItemId(items.getItemID().intValue());
    modelItem.setDescription(items.getDescription());
    modelItem.setValue(items.getValue());
    return modelItem;
  }

  private static XMLGregorianCalendar convertFromDateToSoap(LocalDate date)
      throws DatatypeConfigurationException {
    return DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(date.toString());
  }

  private static LocalDate convertFromSoapToDate(XMLGregorianCalendar xcal) {
    return xcal.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }
}


