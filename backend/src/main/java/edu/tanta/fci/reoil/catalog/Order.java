package edu.tanta.fci.reoil.catalog;


import edu.tanta.fci.reoil.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "order_id_generator"
  )
  @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq")
  private Long id;

  @OneToOne
  private User user;

  @OneToMany(fetch = FetchType.EAGER)
  private List<OrderLine> orderLines;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private UUID trackingNumber;

  public Order(Cart cart) {
    this.user = cart.getUser();
    this.orderLines = new ArrayList<>(cart.getOrderLines());
    this.orderStatus = OrderStatus.PENDING;
    trackingNumber = UUID.randomUUID();
  }
}
