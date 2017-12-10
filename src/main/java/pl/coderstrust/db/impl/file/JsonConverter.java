package pl.coderstrust.db.impl.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

class JsonConverter {
  
  private ObjectMapper objectMapper = new ObjectMapper();
  
  
  String objectToJson(Object object) throws IOException {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return objectMapper.writeValueAsString(object);
  }
  
  Object jsonToObject(String string) throws IOException {
    return objectMapper.readValue(string, Object.class);
  }
}

