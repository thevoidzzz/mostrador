package mostrador.order;

import java.util.Locale;

public enum OrderStatus {
  RECEIVED,
  DELIVERED;

  public String text() {
    return name().toLowerCase(Locale.ROOT);
  }

  public static OrderStatus fromText(String text) {
    return OrderStatus.valueOf(text.toUpperCase(Locale.ROOT));
  }
}
