package com.example.reoil.domain.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority implements GrantedAuthority {

  @SequenceGenerator(
      name = "authority_id_generator",
      allocationSize = 1,
      sequenceName = "authority_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "authority_id_generator"
  )
  @Id
  private Long id;

  private String permission;

  @ManyToMany(mappedBy = "authorities")
  private Set<Role> roles;

  @Override
  public String getAuthority() {
    return this.permission;
  }
}