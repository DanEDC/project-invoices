package pl.coderstrust;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderstrust.rest.InvoiceController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SmokeTest {
  
  @Autowired
  private InvoiceController controller;
  
  @Test
  public void shouldCreateNotNullController() throws Exception {
    assertThat(controller).isNotNull();
  }
}
