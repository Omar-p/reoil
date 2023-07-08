package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public List<OrderDto> getOrders(String status) {
    if (status == null) return orderRepository.findAll()
        .stream().map(this::toOrderDto)
        .toList();

    return orderRepository.findAllByOrderStatus(status)
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




  public void cancelOrder(UUID trackingNumber) {
    final Order order = orderRepository.findByTrackingNumber(trackingNumber)
        .orElseThrow(() -> new NotFoundException("Order not found"));
    if (!order.getOrderStatus().equals(OrderStatus.PENDING)) {
      throw new IllegalStateException("Order can only be cancelled if it is pending");
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);

  }
}
