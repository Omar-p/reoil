package edu.tanta.fci.reoil.catalog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(List<OrderLine> orderLines, OrderStatus orderStatus, LocalDateTime createdAt, UUID trackingNumber) {

}
