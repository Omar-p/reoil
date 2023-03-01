package edu.tanta.fci.reoil.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class User {

  @Id
  private Long id;

  private String username;

  private String email;

  private String password;

  private String phoneNumber;

  private long points;

  private long usedPoints;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @Version
  private int version;
}
