package soap;


import io.spring.guides.gs_producing_web_service.GetInvoiceByIDRequest;
import io.spring.guides.gs_producing_web_service.GetInvoiceByIDResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.logic.InvoiceBook;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceEndpoint(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIDRequest")
  @ResponsePayload
  public GetInvoiceByIDResponse getInvoiceByID(@RequestPayload GetInvoiceByIDRequest request) {
    GetInvoiceByIDResponse response = new GetInvoiceByIDResponse();
    response.setInvoice(invoiceBook.getInvoiceById(request.getInvoiceID()));

    return response;
  }
}