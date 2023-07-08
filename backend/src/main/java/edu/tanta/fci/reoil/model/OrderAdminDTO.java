package edu.tanta.fci.reoil.model;

import edu.tanta.fci.reoil.catalog.OrderLine;
import edu.tanta.fci.reoil.catalog.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderAdminDTO {
  private Long userId;
  private Long orderId;
  private String username;
  private List<OrderLine> orderLines;
  private OrderStatus orderStatus;
  private LocalDateTime createdAt;
  private UUID trackingNumber;

  public OrderAdminDTO(
      Long userId,

      Long orderId,

      String username,

      List<OrderLine> orderLines,

      OrderStatus orderStatus,

      LocalDateTime createdAt,

      UUID trackingNumber
  ) {
    this.userId = userId;
    this.orderId = orderId;
    this.username = username;
    this.orderLines = orderLines;
    this.orderStatus = orderStatus;
    this.createdAt = createdAt;
    this.trackingNumber = trackingNumber;
  }



  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (OrderAdminDTO) obj;
    return Objects.equals(this.userId, that.userId) &&
        Objects.equals(this.username, that.username) &&
        Objects.equals(this.orderLines, that.orderLines) &&
        Objects.equals(this.orderStatus, that.orderStatus) &&
        Objects.equals(this.createdAt, that.createdAt) &&
        Objects.equals(this.trackingNumber, that.trackingNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, orderLines, orderStatus, createdAt, trackingNumber);
  }

  @Override
  public String toString() {
    return "OrderAdminDTO[" +
        "userId=" + userId + ", " +
        "username=" + username + ", " +
        "orderLines=" + orderLines + ", " +
        "orderStatus=" + orderStatus + ", " +
        "createdAt=" + createdAt + ", " +
        "trackingNumber=" + trackingNumber + ']';
  }


}
