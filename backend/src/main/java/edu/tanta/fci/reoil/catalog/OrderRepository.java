package edu.tanta.fci.reoil.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

//  @Query("select new edu.tanta.fci.reoil.catalog.OrderDto(o.orderLines, o.orderStatus, o.createdAt, o.trackingNumber) from Order o")
//  List<OrderDto> findOrderDtos();
//
//  @Query("select new edu.tanta.fci.reoil.catalog.OrderDto(o.orderLines, o.orderStatus, o.createdAt, o.trackingNumber) from Order o where o.trackingNumber = ?1")
//  OrderDto findOrderDtoById(UUID trackingNumber);

  Optional<Order> findByTrackingNumber(UUID trackingNumber);
}
