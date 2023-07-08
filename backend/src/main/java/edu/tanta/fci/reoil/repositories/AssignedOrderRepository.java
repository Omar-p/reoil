package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.catalog.OrderStatus;
import edu.tanta.fci.reoil.domain.AssignedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssignedOrderRepository extends JpaRepository<AssignedOrder, Long> {

  List<AssignedOrder> findAllByWorker_Id(Long workerId);


  List<AssignedOrder> findAllByWorker_IdAndOrder_OrderStatus(Long workerId, OrderStatus orderStatus);

}

