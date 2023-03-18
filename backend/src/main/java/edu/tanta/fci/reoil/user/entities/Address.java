package edu.tanta.fci.reoil.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {

  @SequenceGenerator(
      name = "address_id_generator",
      allocationSize = 1,
      sequenceName = "address_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "address_id_generator"
  )
  @Id
  private Long id;

  private String title;

  private boolean main;

  private String address;

  private String fullName;

  private String phoneNumber;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "address_user_fkey"))
  private User user;

  @Version
  private int version;


  public Address(String title, String address, boolean main, String fullName, String phoneNumber) {
    this.title = title;
    this.main = main;
    this.address = address;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }
}
