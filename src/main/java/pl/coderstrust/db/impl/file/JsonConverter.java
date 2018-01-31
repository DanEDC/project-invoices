package pl.coderstrust.db.impl.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.config.ObjectMapperProvider;
import pl.coderstrust.model.Invoice;

import java.io.IOException;

@Service
class JsonConverter {
  
  private static Logger logger = LoggerFactory.getLogger(JsonConverter.class);
  
  private ObjectMapper objectMapper;
  
  public JsonConverter(ObjectMapperProvider objectMapperProvider) {
    logger.debug("JsonConverter called");
    this.objectMapper = objectMapperProvider.objectMapper();
  }

  String objectToJson(Object object) {
    logger.debug("JsonConverter called");
    String json = null;
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    try {
      json = objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      logger.warn("Not able to convert to json : " + object.toString(), e);
    }
    return json;
  }
  
  Invoice stringToInvoice(String string) {
    logger.debug("JsonConverter called");
    Invoice invoice = null;
    try {
      invoice = objectMapper.readValue(string, Invoice.class);
    } catch (IOException e) {
      logger.warn("Not able to string to Invoice : " + string, e);
    }
    return invoice;
  }
}