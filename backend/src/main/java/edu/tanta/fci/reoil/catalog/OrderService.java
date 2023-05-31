package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public List<OrderDto> getOrders() {
    return orderRepository.findAll()
        .stream().map(this::toOrderDto)
        .toList();
  }

  public OrderDto getOrderById(UUID trackingNumber) {
    final Order order = orderRepository.findByTrackingNumber(trackingNumber)
        .orElseThrow(() -> new NotFoundException("Order not found"));
    return toOrderDto(order);
  }

  private OrderDto toOrderDto(Order order) {
    return new OrderDto(order.getOrderLines(), order.getOrderStatus(), order.getCreatedAt(), order.getTrackingNumber());
  }


}
