package edu.tanta.fci.reoil.password;

import jakarta.persistence.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Entity
@Table(name = "reset_password_codes")
public class ResetPasswordCode{

  private static final Random RANDOM = new SecureRandom(LocalDateTime.now().toString().getBytes());
  @Id
  @SequenceGenerator(
      name = "reset_password_codes_generator",
      allocationSize = 1,
      sequenceName = "reset_password_codes_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "reset_password_codes_generator"
  )
  private Long id;

  private int code;

  private String email;

  private LocalDateTime expiryDate;

  public ResetPasswordCode() {
  }

  public ResetPasswordCode(String email) {
    this.code = RANDOM.nextInt(1000, 10_000);
    this.email = email;
    this.expiryDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
  }

  public Long getId() {
    return id;
  }

  public int getCode() {
    return code;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getExpiryDate() {
    return expiryDate;
  }
}
