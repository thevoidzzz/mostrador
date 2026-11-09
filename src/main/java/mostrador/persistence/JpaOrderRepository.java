package mostrador.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import mostrador.order.Order;
import mostrador.order.OrderRepository;
import mostrador.order.OrderStatus;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderRepository implements OrderRepository {

  private final PickupOrderJpa rows;

  public JpaOrderRepository(PickupOrderJpa rows) {
    this.rows = rows;
  }

  @Override
  public Optional<Order> findByCode(String code) {
    return rows.findById(code).map(JpaOrderRepository::toOrder);
  }

  @Override
  public Order save(Order order) {
    rows.save(toRow(order));
    return order;
  }

  private static PickupOrderRow toRow(Order order) {
    return new PickupOrderRow(order.code(), order.item(), order.price(), order.status().text());
  }

  private static Order toOrder(PickupOrderRow row) {
    return new Order(row.code(), row.item(), row.price(), OrderStatus.fromText(row.status()));
  }
}

interface PickupOrderJpa extends org.springframework.data.repository.Repository<PickupOrderRow, String> {

  Optional<PickupOrderRow> findById(String code);

  PickupOrderRow save(PickupOrderRow row);
}

@Entity
@Table(name = "pickup_order")
class PickupOrderRow {

  @Id
  private String code;

  @Column(nullable = false)
  private String item;

  @Column(nullable = false)
  private String price;

  @Column(nullable = false)
  private String status;

  protected PickupOrderRow() {}

  PickupOrderRow(String code, String item, String price, String status) {
    this.code = code;
    this.item = item;
    this.price = price;
    this.status = status;
  }

  String code() {
    return code;
  }

  String item() {
    return item;
  }

  String price() {
    return price;
  }

  String status() {
    return status;
  }
}
