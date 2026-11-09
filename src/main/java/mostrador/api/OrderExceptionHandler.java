package mostrador.api;

import java.net.URI;
import mostrador.order.OrderRejection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class OrderExceptionHandler {

  @ExceptionHandler(OrderRejection.class)
  ProblemDetail reject(OrderRejection rejection) {
    HttpStatus status =
        switch (rejection.code()) {
          case OrderRejection.UNKNOWN_ITEM, OrderRejection.INVALID_ORDER -> HttpStatus.BAD_REQUEST;
          case OrderRejection.UNKNOWN_ORDER -> HttpStatus.NOT_FOUND;
          case OrderRejection.ALREADY_REGISTERED, OrderRejection.ALREADY_DELIVERED -> HttpStatus.CONFLICT;
          default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    ProblemDetail problem = ProblemDetail.forStatus(status);
    problem.setType(URI.create("urn:mostrador:" + rejection.code()));
    problem.setTitle(rejection.code());
    problem.setProperty("code", rejection.code());
    return problem;
  }
}
