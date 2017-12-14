package pl.coderstrust.model;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.logic.InvoiceBook;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceBook invoiceBook;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    when(invoiceBook.getAllInvoices()).thenReturn(Arrays.asList(
        new Invoice(LocalDate.now(), new Company("", ""), new Company("", ""), Arrays.asList())));
    this.mockMvc
        .perform(get("/invoices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }
  //Test nr 2, jeszcze nie dziala
//  @Test
//  public void shouldReturnDefaultMessage2()throws Exception {
//
//    this.mockMvc
//        .perform(get("/invoices"))
//        .andDo(print())
//        .andExpect(content().contentType("application/json;charset=UTF-8"))
//        .andExpect(jsonPath("$", hasSize(2)))
//        .andExpect(jsonPath("$[0].invoiceID.id",is(nullValue())));
//  }
}
