package edu.tanta.fci.reoil.worker.model;

import edu.tanta.fci.reoil.catalog.OrderLine;
import edu.tanta.fci.reoil.catalog.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AssignedOrderForWorker(Long assignedOrderId, List<OrderLine> orderLines, OrderStatus orderStatus,  Long userId, String username, LocalDateTime assignedAt) {


}
