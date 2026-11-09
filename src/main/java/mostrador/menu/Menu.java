package mostrador.menu;

import mostrador.order.OrderRejection;

public final class Menu {

  private Menu() {}

  public static String priceOf(String item) {
    if ("coffee".equals(item)) {
      return "4.50";
    }
    if ("sandwich".equals(item)) {
      return "6.00";
    }
    throw OrderRejection.unknownItem();
  }
}
