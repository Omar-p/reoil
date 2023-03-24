package edu.tanta.fci.reoil.domain;

import edu.tanta.fci.reoil.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Donation {

  @Id
  @GeneratedValue(generator = "donation_id_generator")
  @SequenceGenerator(
      name = "donation_id_generator",
      sequenceName = "donation_id_sequence",
      allocationSize = 1
  )
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Charity charity;

  @Positive
  private long amount;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  private Program program;

  public Donation(User user, Charity charity, Program program, long amount) {
    this.user = user;
    this.charity = charity;
    this.program = program;
    this.amount = amount;
  }
}
