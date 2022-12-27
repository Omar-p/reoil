package com.example.reoil.domain.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
  @SequenceGenerator(
      name = "role_id_generator",
      allocationSize = 1,
      sequenceName = "role_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "role_id_generator"
  )
  @Id
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @Singular
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(name = "role_authority",
      joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
  private Set<Authority> authorities;

}

//  public Set<? extends GrantedAuthority> getGrantedAuthorities() {
//    return Set.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
//  }