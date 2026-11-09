package mostrador.order;

import mostrador.menu.Menu;

public final class OrderService {

  private final OrderRepository orders;

  public OrderService(OrderRepository orders) {
    this.orders = orders;
  }

  public Order register(String code, String item) {
    if (code == null || code.isBlank()) {
      throw OrderRejection.invalidOrder();
    }
    String price = Menu.priceOf(item);
    if (orders.findByCode(code).isPresent()) {
      throw OrderRejection.alreadyRegistered();
    }
    return orders.save(Order.received(code, item, price));
  }
}
