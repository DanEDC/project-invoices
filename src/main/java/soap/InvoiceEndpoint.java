package soap;


import io.spring.guides.gs_producing_web_service.GetInvoiceRequest;
import io.spring.guides.gs_producing_web_service.GetInvoiceResponse;
import io.spring.guides.gs_producing_web_service.PostInvoiceRequest;
import io.spring.guides.gs_producing_web_service.PostInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.logic.InvoiceBook;
import pl.coderstrust.model.Invoice;

import java.math.BigInteger;

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

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postInvoiceRequest")
  @ResponsePayload
  public PostInvoiceResponse postInvoice(@RequestPayload PostInvoiceRequest request) {
    PostInvoiceResponse response = new PostInvoiceResponse();
    io.spring.guides.gs_producing_web_service.Invoice invoice = invoiceBook
        .saveInvoice(ClassConverter.convertFromSoapToInvoice.apply(request.setInvoice()));
    response.getInvoice(ClassConverter.convertFromSoapToInvoice.apply(invoice));
    return response;
  }
}