package edu.tanta.fci.reoil.user.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "verification_username_key", columnNames = "username")
    }
)
public class Verification {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(length = 36)
  private String id;

  @Column(nullable = false)
  private String username;

  protected Verification() {
  }

  public Verification(String username) {
    this.username = username;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }
}
