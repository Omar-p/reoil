package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "cart_id_generator"
  )
  @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_seq")
  private Long id;

  @OneToOne
  private User user;

  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<OrderLine> orderLines = new ArrayList<>();

  public void addOrderLine(OrderLine orderLine) {
    orderLines.add(orderLine);
  }

  public void removeOrderLine(Long id) {
    orderLines.
        removeIf(orderLine -> orderLine.getId().equals(id));
  }

  public void clear() {
    orderLines.clear();
  }
}
