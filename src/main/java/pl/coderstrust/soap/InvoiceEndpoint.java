package pl.coderstrust.soap;


import io.spring.guides.gs_producing_web_service.AddInvoiceRequest;
import io.spring.guides.gs_producing_web_service.AddInvoiceResponse;
import io.spring.guides.gs_producing_web_service.DeleteInvoiceRequest;
import io.spring.guides.gs_producing_web_service.DeleteInvoiceResponse;
import io.spring.guides.gs_producing_web_service.GetInvoiceRequest;
import io.spring.guides.gs_producing_web_service.GetInvoiceResponse;
import io.spring.guides.gs_producing_web_service.UpdateInvoiceRequest;
import io.spring.guides.gs_producing_web_service.UpdateInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceEndpoint(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request) {
    GetInvoiceResponse response = new GetInvoiceResponse();
    Invoice invoice = invoiceBook.getInvoiceById(request.getInvoiceID().intValue());
    response.setInvoice(ClassConverter.convertFromInvoiceToSoap.apply(invoice));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addInvoiceRequest")
  @ResponsePayload
  public AddInvoiceResponse addInvoice(@RequestPayload AddInvoiceRequest request) {
    AddInvoiceResponse response = new AddInvoiceResponse();
    Integer invoiceID = invoiceBook.saveInvoice(ClassConverter.convertFromSoapToInvoice.apply(request.getInvoice()));
    response.setName("Invoice has been created");
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInvoiceRequest")
  @ResponsePayload
  public UpdateInvoiceResponse updateInvoice(@RequestPayload UpdateInvoiceRequest request) {
    UpdateInvoiceResponse response = new UpdateInvoiceResponse();
    Invoice invoice = invoiceBook.getInvoiceById(request.getInvoiceID().intValue());
    Invoice updatedInvoice = invoiceBook.updateInvoice(invoice, (request.getInvoiceID().intValue()));
    response.setInvoice(ClassConverter.convertFromInvoiceToSoap.apply(updatedInvoice));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceRequest")
  @ResponsePayload
  public DeleteInvoiceResponse deleteInvoice(@RequestPayload DeleteInvoiceRequest request) {
    DeleteInvoiceResponse response = new DeleteInvoiceResponse();
    Invoice invoice = invoiceBook.removeInvoiceById(request.getInvoiceID().intValue());
    response.setName("Invoice has been deleted");
    return response;
  }
}