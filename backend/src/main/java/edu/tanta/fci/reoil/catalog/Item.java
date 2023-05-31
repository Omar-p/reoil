package edu.tanta.fci.reoil.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "item_id_generator"
  )
  @SequenceGenerator(name = "item_id_generator", sequenceName = "item_id_seq")
  private Long id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String imageUrl;

  private Double quantity;

  private Long points;

  private String unit;

  public Item(String name, Double quantity, Long points, String unit) {
    this.name = name;
    this.quantity = quantity;
    this.points = points;
    this.unit = unit;
  }


}
