package mostrador.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrderHttpTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void registersCoffeeAsReceived() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"coffee-1\",\"item\":\"coffee\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value("coffee-1"))
        .andExpect(jsonPath("$.item").value("coffee"))
        .andExpect(jsonPath("$.price").value("4.50"))
        .andExpect(jsonPath("$.status").value("received"));
  }

  @Test
  void registersSandwichAtSix() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"sandwich-1\",\"item\":\"sandwich\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.price").value("6.00"))
        .andExpect(jsonPath("$.item").value("sandwich"))
        .andExpect(jsonPath("$.status").value("received"));
  }

  @Test
  void deliversAReceivedOrder() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"deliver-1\",\"item\":\"coffee\"}"))
        .andExpect(status().isCreated());

    mvc.perform(post("/orders/deliver-1/delivery"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("deliver-1"))
        .andExpect(jsonPath("$.item").value("coffee"))
        .andExpect(jsonPath("$.price").value("4.50"))
        .andExpect(jsonPath("$.status").value("delivered"));
  }

  @Test
  void rejectsAnItemOffTheMenu() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"tea-1\",\"item\":\"tea\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.type").value("urn:mostrador:unknown-item"))
        .andExpect(jsonPath("$.title").value("unknown-item"))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.code").value("unknown-item"));
  }

  @Test
  void rejectsABlankCode() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"  \",\"item\":\"coffee\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.code").value("invalid-order"));
  }

  @Test
  void rejectsAnUnknownCode() throws Exception {
    mvc.perform(post("/orders/missing-1/delivery"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.code").value("unknown-order"))
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void keepsTheFirstOrderWhenTheCodeIsRegisteredTwice() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"twice-1\",\"item\":\"coffee\"}"))
        .andExpect(status().isCreated());

    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"twice-1\",\"item\":\"sandwich\"}"))
        .andExpect(status().isConflict())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.code").value("already-registered"));

    mvc.perform(post("/orders/twice-1/delivery"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.item").value("coffee"))
        .andExpect(jsonPath("$.price").value("4.50"))
        .andExpect(jsonPath("$.status").value("delivered"));
  }

  @Test
  void rejectsASecondDelivery() throws Exception {
    mvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"again-1\",\"item\":\"sandwich\"}"))
        .andExpect(status().isCreated());

    mvc.perform(post("/orders/again-1/delivery")).andExpect(status().isOk());

    mvc.perform(post("/orders/again-1/delivery"))
        .andExpect(status().isConflict())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.code").value("already-delivered"))
        .andExpect(jsonPath("$.status").value(409));
  }
}
