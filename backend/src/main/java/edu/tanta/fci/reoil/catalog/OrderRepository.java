package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.model.OrderAdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

  @Query(value = "select * from orders  where order_status = upper(:status)", nativeQuery = true)
  List<Order> findAllByOrderStatus(@Param("status") String orderStatus);




// @Query(value = "select new edu.tanta.fci.reoil.model.OrderAdminDTO(o.user.id, o.user.username, o.orderLines, o.orderStatus, o.createdAt, o.trackingNumber) from Order o order by o.createdAt desc ",
//      countQuery = "select count(o) from Order o")
//  List<OrderAdminDTO> findAllOrdersForAdmin();
//
//  @Query(value = "select new edu.tanta.fci.reoil.model.OrderAdminDTO(o.user.id, o.user.username, o.orderLines, o.orderStatus, o.createdAt, o.trackingNumber) from Order o where o.orderStatus = edu.tanta.fci.reoil.catalog.OrderStatus.PENDING order by o.createdAt desc",
//      countQuery = "select count(o) from Order o where o.orderStatus = edu.tanta.fci.reoil.catalog.OrderStatus.PENDING")
//  Page<OrderAdminDTO> findAllPendingOrdersForAdmin(Pageable pageable);


}
