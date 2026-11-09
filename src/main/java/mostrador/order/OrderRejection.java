package mostrador.order;

public final class OrderRejection extends RuntimeException {

  public static final String UNKNOWN_ITEM = "unknown-item";
  public static final String INVALID_ORDER = "invalid-order";
  public static final String ALREADY_REGISTERED = "already-registered";
  public static final String UNKNOWN_ORDER = "unknown-order";
  public static final String ALREADY_DELIVERED = "already-delivered";

  private final String code;

  private OrderRejection(String code) {
    super(code);
    this.code = code;
  }

  public String code() {
    return code;
  }

  public static OrderRejection unknownItem() {
    return new OrderRejection(UNKNOWN_ITEM);
  }

  public static OrderRejection invalidOrder() {
    return new OrderRejection(INVALID_ORDER);
  }

  public static OrderRejection alreadyRegistered() {
    return new OrderRejection(ALREADY_REGISTERED);
  }

  public static OrderRejection unknownOrder() {
    return new OrderRejection(UNKNOWN_ORDER);
  }

  public static OrderRejection alreadyDelivered() {
    return new OrderRejection(ALREADY_DELIVERED);
  }
}
