package edu.tanta.fci.reoil.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {

  @Id
  private Long id;

  private String title;

  private boolean main;

  private String address;
}
