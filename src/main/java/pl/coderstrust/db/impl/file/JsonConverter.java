package pl.coderstrust.db.impl.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import pl.coderstrust.model.Invoice;

class JsonConverter {

  private ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  String objectToJson(Object object) {
    String json = null;
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    try {
      json = objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  Invoice jsonBytesToInvoice(byte[] bytes) {
    Invoice invoice = null;
    try {
      invoice = objectMapper.readValue(bytes, Invoice.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return invoice;
  }

  Invoice jsonStringToInvoice(String string) {
    Invoice invoice = null;
    try {
      invoice = objectMapper.readValue(string, Invoice.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return invoice;
  }

  Object jsonToObject(byte[] bytes) {
    Object object = null;
    try {
      object = objectMapper.readValue(bytes, Object.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return object;
  }
}