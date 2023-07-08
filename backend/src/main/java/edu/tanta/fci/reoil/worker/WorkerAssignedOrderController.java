package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.catalog.OrderStatus;
import edu.tanta.fci.reoil.domain.AssignedOrder;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.repositories.AssignedOrderRepository;
import edu.tanta.fci.reoil.worker.model.AssignedOrderForWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/worker/assigned-order")
@PreAuthorize("hasAuthority('SCOPE_WORKER')")
@RequiredArgsConstructor
public class WorkerAssignedOrderController {

  private final AssignedOrderRepository assignedOrderRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @GetMapping
  public Map<String, Iterable<AssignedOrderForWorker>> getAllAssignedOrders(@AuthenticationPrincipal Jwt jwt) {
    final List<AssignedOrderForWorker> assignedOrdersForWorkers = assignedOrderRepository
        .findAllByWorker_Id(jwt.getClaim("id"))
        .stream()
        .map(ao -> new AssignedOrderForWorker(ao.getId(), ao.getOrder().getOrderLines(), ao.getOrder().getOrderStatus(), ao.getOrder().getUser().getId(), ao.getOrder().getUser().getUsername(), ao.getAssignedAt()))
        .toList();
    return Map.of("assignedOrders", assignedOrdersForWorkers);
  }


  @GetMapping("/in-progress")
  public Map<String, List<AssignedOrderForWorker>> getInProgressAssignedOrders(@AuthenticationPrincipal Jwt jwt) {
    final List<AssignedOrderForWorker> assignedOrderForWorkers = assignedOrderRepository.findAllByWorker_IdAndOrder_OrderStatus(jwt.getClaim("id"), OrderStatus.IN_PROGRESS)
        .stream()
        .map(ao -> new AssignedOrderForWorker(ao.getId(), ao.getOrder().getOrderLines(), ao.getOrder().getOrderStatus(), ao.getOrder().getUser().getId(), ao.getOrder().getUser().getUsername(), ao.getAssignedAt()))
        .toList();
    return Map.of("inProgressAssignedOrders", assignedOrderForWorkers);
  }

  @PostMapping("/accept/{assignedOrderId}")
  public void acceptAssignedOrder(@AuthenticationPrincipal Jwt jwt, @PathVariable("assignedOrderId") Long assignedOrderId) {
    var assignedOrder = assignedOrderRepository.findById(assignedOrderId).orElseThrow(
        () -> new NotFoundException("Assigned order with id %s not found".formatted(assignedOrderId))
    );

    if (assignedOrder.getWorker().getId() != jwt.getClaim("id")) {
      throw new AccessDeniedException("You are not allowed to accept this order");
    }

    if (assignedOrder.getOrder().getOrderStatus() != OrderStatus.IN_PROGRESS) {
      throw new IllegalStateException("You are not allowed to accept this order");
    }

    assignedOrder.getOrder().setOrderStatus(OrderStatus.ACCEPTED);
    assignedOrderRepository.save(assignedOrder);
    applicationEventPublisher.publishEvent(new AssignedOrderAcceptedEvent(Map.of("order", assignedOrder.getOrder(),
        "user", assignedOrder.getOrder().getUser())));
  }

  @PostMapping("/reject/{assignedOrderId}")
  public void rejectAssignedOrder(@AuthenticationPrincipal Jwt jwt, @PathVariable("assignedOrderId") Long assignedOrderId) {
    var assignedOrder = assignedOrderRepository.findById(assignedOrderId).orElseThrow(
        () -> new NotFoundException("Assigned order with id %s not found".formatted(assignedOrderId))
    );

    if (assignedOrder.getWorker().getId() != jwt.getClaim("id")) {
      throw new AccessDeniedException("You are not allowed to reject this order");
    }

    if (assignedOrder.getOrder().getOrderStatus() != OrderStatus.IN_PROGRESS) {
      throw new IllegalStateException("You are not allowed to reject this order");
    }

    assignedOrder.getOrder().setOrderStatus(OrderStatus.REJECTED);
    assignedOrderRepository.save(assignedOrder);
    applicationEventPublisher.publishEvent(new AssignedOrderRejectedEvent(Map.of("order", assignedOrder.getOrder(),
        "user", assignedOrder.getOrder().getUser())));
  }


  @GetMapping("/accepted")
  public Map<String, List<AssignedOrderForWorker>> getAcceptedAssignedOrders(@AuthenticationPrincipal Jwt jwt) {
    final List<AssignedOrderForWorker> assignedOrderForWorkers = assignedOrderRepository.findAllByWorker_IdAndOrder_OrderStatus(jwt.getClaim("id"), OrderStatus.ACCEPTED)
        .stream()
        .map(ao -> new AssignedOrderForWorker(ao.getId(), ao.getOrder().getOrderLines(), ao.getOrder().getOrderStatus(), ao.getOrder().getUser().getId(), ao.getOrder().getUser().getUsername(), ao.getAssignedAt()))
        .toList();
    return Map.of("finishedAssignedOrders", assignedOrderForWorkers) ;
  }
}
