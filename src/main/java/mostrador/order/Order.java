package mostrador.order;

public record Order(String code, String item, String price, OrderStatus status) {

  public static Order received(String code, String item, String price) {
    return new Order(code, item, price, OrderStatus.RECEIVED);
  }

  public Order delivered() {
    return new Order(code, item, price, OrderStatus.DELIVERED);
  }
}
