package edu.tanta.fci.reoil.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_USER')")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public List<OrderDto> getOrders(@RequestParam(required = false) String status) {
    return orderService.getOrders(status);
  }

  @GetMapping("/{trackingNumber}")
  public OrderDto getOrderById(@PathVariable("trackingNumber") UUID trackingNumber) {
    return orderService.getOrderById(trackingNumber);
  }

  @PostMapping("/{trackingNumber}")
  public void cancelOrder(@PathVariable("trackingNumber") UUID trackingNumber) {
    orderService.cancelOrder(trackingNumber);
  }
}
