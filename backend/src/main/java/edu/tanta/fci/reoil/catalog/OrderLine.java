package edu.tanta.fci.reoil.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderLine {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "order_line_id_generator"
  )
  @SequenceGenerator(name = "order_line_id_generator", sequenceName = "order_line_id_seq")
  private Long id;
  @OneToOne
  private Item item;

  private Long quantity;

}
