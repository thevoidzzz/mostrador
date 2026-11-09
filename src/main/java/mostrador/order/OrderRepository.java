package mostrador.order;

import java.util.Optional;

public interface OrderRepository {

  Optional<Order> findByCode(String code);

  Order save(Order order);
}
