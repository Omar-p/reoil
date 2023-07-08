package edu.tanta.fci.reoil.admin;

import edu.tanta.fci.reoil.admin.requests.AssignOrderRequest;
import edu.tanta.fci.reoil.catalog.OrderRepository;
import edu.tanta.fci.reoil.catalog.OrderStatus;
import edu.tanta.fci.reoil.domain.AssignedOrder;
import edu.tanta.fci.reoil.repositories.AssignedOrderRepository;
import edu.tanta.fci.reoil.worker.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AdminWorkerService {

  private final WorkerRepository workerRepository;
  private final OrderRepository orderRepository;

  private final AssignedOrderRepository assignedOrderRepository;


  public Iterable<WorkerDTO> getDisabledWorkersAccounts() {
    return workerRepository.findAllByEnabledIs(false);
  }

  public void enableWorkerAccount(Long workerId) {
    var w = workerRepository.findById(workerId).orElseThrow(
        () -> new RuntimeException("Worker with id %s not found".formatted(workerId))
    );

    w.setEnabled(true);
    workerRepository.save(w);
  }


  public Iterable<WorkerDTO> getEnabledWorkersAccounts() {
    return workerRepository.findAllByEnabledIs(true);
  }

  public void assignOrder(AssignOrderRequest assignOrderRequest) {
    var w = workerRepository.findById(assignOrderRequest.workerId()).orElseThrow(
        () -> new RuntimeException("Worker with id %s not found".formatted(assignOrderRequest.workerId()))
    );

    var o = orderRepository.findById(assignOrderRequest.orderId()).orElseThrow(
        () -> new RuntimeException("Order with id %s not found".formatted(assignOrderRequest.orderId()))
    );

    o.setOrderStatus(OrderStatus.IN_PROGRESS);
    var assignedOrder = new AssignedOrder(o, w);
    assignedOrder.setAssignedAt(LocalDateTime.now());
    assignedOrderRepository.save(assignedOrder);

  }
}
