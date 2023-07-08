package edu.tanta.fci.reoil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tanta.fci.reoil.catalog.Order;
import edu.tanta.fci.reoil.catalog.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
  @jakarta.persistence.UniqueConstraint(columnNames = {"order_id", "worker_id"},
      name = "assigned_order_worker_order_key")
})
@Getter
@Setter
@NoArgsConstructor
public class AssignedOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned_order_id_generator")
  @SequenceGenerator(
      name = "assigned_order_id_generator",
      sequenceName = "assigned_order_id_seq",
      allocationSize = 1
  )
  private Long id;

  @OneToOne
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "assigned_order_worker_id_fkey"))
  private Order order;

  @OneToOne
  @JoinColumn(name = "worker_id", foreignKey = @ForeignKey(name = "assigned_order_worker_id_fkey"))
  private Worker worker;

  @CreationTimestamp
  private LocalDateTime assignedAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  private OrderStatus finalStatus;

  public AssignedOrder(Order order, Worker worker) {
    this.order = order;
    this.worker = worker;
  }


}
