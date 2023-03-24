package edu.tanta.fci.reoil.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Program {

  @SequenceGenerator(
      name = "program_id_generator",
      allocationSize = 1,
      sequenceName = "program_id_sequence"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "program_id_generator"
  )
  @Id
  private Long id;

  private String name;

  @ManyToOne
  private Charity charity;


  public Program(String name) {
    this.name = name;
  }
}
