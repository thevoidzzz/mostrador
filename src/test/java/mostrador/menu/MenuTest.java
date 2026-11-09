package mostrador.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mostrador.order.OrderRejection;
import org.junit.jupiter.api.Test;

class MenuTest {

  @Test
  void pricesStayFixedText() {
    assertEquals("4.50", Menu.priceOf("coffee"));
    assertEquals("6.00", Menu.priceOf("sandwich"));
  }

  @Test
  void rejectsAnItemOffTheMenu() {
    OrderRejection rejection = assertThrows(OrderRejection.class, () -> Menu.priceOf("tea"));
    assertEquals(OrderRejection.UNKNOWN_ITEM, rejection.code());
  }
}
