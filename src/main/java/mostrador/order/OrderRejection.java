package mostrador.order;

public final class OrderRejection extends RuntimeException {

  public static final String UNKNOWN_ITEM = "unknown-item";

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
}
