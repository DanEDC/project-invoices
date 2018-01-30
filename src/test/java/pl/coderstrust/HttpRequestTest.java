//package pl.coderstrust;
//
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.coderstrust.model.Company;
//import pl.coderstrust.model.Invoice;
//import pl.coderstrust.model.Item;
//import pl.coderstrust.model.Vat;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class HttpRequestTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @Test
//  public void shouldReturnDefaultMessage() throws Exception {
//
//    Invoice invoice = getMockOfInvoice();
//
//    this.mockMvc
//        .perform(post("/invoices/" + invoice));
//
//    this.mockMvc
//        .perform(get("/invoices/{id=1}"))
//        .andDo(print())
//        .andExpect(status().isOk())
//        .andExpect(content().contentType("application/json;charset=UTF-8"));
//  }
//
//  private Invoice getMockOfInvoice() {
//    List<Item> items = new ArrayList<>();
//    Item hammer = new Item("Hammer", 50.0, Vat.vat5);
//    Item anvil = new Item("Anvil", 500.0, Vat.vat8);
//    items.add(hammer);
//    items.add(anvil);
//    Invoice invoice = mock(Invoice.class);
//    invoice.setItems(items);
//    invoice.setDate(LocalDate.now());
//    Company buyer = new Company("BlackSmith", "HammerFist");
//    Company seller = new Company("IronShop", "IronLife");
//    return invoice;
//  }
//}