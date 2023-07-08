package edu.tanta.fci.reoil.admin;

import edu.tanta.fci.reoil.catalog.OrderRepository;
import edu.tanta.fci.reoil.catalog.OrderStatus;
import edu.tanta.fci.reoil.model.OrderAdminDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@RequestMapping("/api/v1/admin/orders")
public class AdminOrdersController {

  private final OrderRepository orderRepository;

  public AdminOrdersController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @GetMapping
  public List<OrderAdminDTO> getOrders() {


    return orderRepository.findAll()
        .stream().map(o -> new OrderAdminDTO(o.getUser().getId(), o.getId(), o.getUser().getUsername(), o.getOrderLines(), o.getOrderStatus(), o.getCreatedAt(), o.getTrackingNumber()))
        .peek(o -> System.out.println(o))
        .toList();


  }

  @GetMapping("/pending")
  public List<OrderAdminDTO> getPendingOrders() {


    return orderRepository.findAllByOrderStatus(OrderStatus.PENDING.name())
        .stream()
        .map(o -> new OrderAdminDTO(o.getUser().getId(), o.getId(), o.getUser().getUsername(), o.getOrderLines(), o.getOrderStatus(), o.getCreatedAt(), o.getTrackingNumber()))
        .peek(o -> System.out.println(o))

        .toList();
  }

}
