package mostrador.api;

import mostrador.order.Order;
import mostrador.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orders;

  public OrderController(OrderService orders) {
    this.orders = orders;
  }

  @PostMapping("/orders")
  public ResponseEntity<OrderResponse> register(@RequestBody RegisterRequest request) {
    Order order = orders.register(request.code(), request.item());
    return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
  }

  @PostMapping("/orders/{code}/delivery")
  public OrderResponse deliver(@PathVariable String code) {
    return OrderResponse.from(orders.deliver(code));
  }

  public record RegisterRequest(String code, String item) {}

  public record OrderResponse(String code, String item, String price, String status) {
    static OrderResponse from(Order order) {
      return new OrderResponse(order.code(), order.item(), order.price(), order.status().text());
    }
  }
}
