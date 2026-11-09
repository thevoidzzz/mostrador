package mostrador.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock
  private OrderRepository orders;

  private OrderService service;

  @BeforeEach
  void setUp() {
    service = new OrderService(orders);
  }

  @Test
  void registersCoffeeAsReceivedAtFourFifty() {
    when(orders.findByCode("A1")).thenReturn(Optional.empty());
    when(orders.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Order order = service.register("A1", "coffee");

    assertEquals("A1", order.code());
    assertEquals("coffee", order.item());
    assertEquals("4.50", order.price());
    assertEquals(OrderStatus.RECEIVED, order.status());
  }

  @Test
  void registersSandwichAtSix() {
    when(orders.findByCode("B1")).thenReturn(Optional.empty());
    when(orders.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Order order = service.register("B1", "sandwich");

    assertEquals("sandwich", order.item());
    assertEquals("6.00", order.price());
    assertEquals(OrderStatus.RECEIVED, order.status());
  }

  @Test
  void rejectsAnItemOffTheMenu() {
    OrderRejection rejection = assertThrows(OrderRejection.class, () -> service.register("A2", "tea"));

    assertEquals(OrderRejection.UNKNOWN_ITEM, rejection.code());
  }

  @Test
  void rejectsABlankCode() {
    OrderRejection blank = assertThrows(OrderRejection.class, () -> service.register("  ", "coffee"));
    OrderRejection missing = assertThrows(OrderRejection.class, () -> service.register(null, "coffee"));

    assertEquals(OrderRejection.INVALID_ORDER, blank.code());
    assertEquals(OrderRejection.INVALID_ORDER, missing.code());
  }

  @Test
  void rejectsRegisteringTheSameCodeTwice() {
    when(orders.findByCode("A1")).thenReturn(Optional.of(Order.received("A1", "coffee", "4.50")));

    OrderRejection rejection = assertThrows(OrderRejection.class, () -> service.register("A1", "sandwich"));

    assertEquals(OrderRejection.ALREADY_REGISTERED, rejection.code());
  }

  @Test
  void deliversAReceivedOrder() {
    when(orders.findByCode("A1")).thenReturn(Optional.of(Order.received("A1", "coffee", "4.50")));
    when(orders.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Order order = service.deliver("A1");

    assertEquals("A1", order.code());
    assertEquals("coffee", order.item());
    assertEquals("4.50", order.price());
    assertEquals(OrderStatus.DELIVERED, order.status());
    assertEquals("delivered", order.status().text());
  }

  @Test
  void rejectsASecondDelivery() {
    when(orders.findByCode("A1"))
        .thenReturn(Optional.of(new Order("A1", "coffee", "4.50", OrderStatus.DELIVERED)));

    OrderRejection rejection = assertThrows(OrderRejection.class, () -> service.deliver("A1"));

    assertEquals(OrderRejection.ALREADY_DELIVERED, rejection.code());
  }

  @Test
  void rejectsAnUnknownCode() {
    when(orders.findByCode("missing")).thenReturn(Optional.empty());

    OrderRejection rejection = assertThrows(OrderRejection.class, () -> service.deliver("missing"));

    assertEquals(OrderRejection.UNKNOWN_ORDER, rejection.code());
  }
}
