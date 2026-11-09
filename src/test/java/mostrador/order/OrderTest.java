package mostrador.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  void aNewOrderIsReceivedAndKeepsItsPriceText() {
    Order order = Order.received("A1", "coffee", "4.50");

    assertEquals("A1", order.code());
    assertEquals("coffee", order.item());
    assertEquals("4.50", order.price());
    assertEquals("received", order.status().text());
  }
}
