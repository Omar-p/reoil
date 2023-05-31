package edu.tanta.fci.reoil.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_USER')")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public List<OrderDto> getOrders() {
    return orderService.getOrders();
  }

  @GetMapping("/{trackingNumber}")
  public OrderDto getOrderById(@PathVariable("trackingNumber") UUID trackingNumber) {
    return orderService.getOrderById(trackingNumber);
  }
}
