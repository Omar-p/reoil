package com.example.reoil.domain.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @SequenceGenerator(
      name = "users_id_generator",
      allocationSize = 1,
      sequenceName = "users_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "users_id_generator"
  )
  @Id
  private Long id;
  private String username;
  private String email;
  private String password;
  private String phone;

  @Singular
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
  private Set<Role> roles;

  @Transient
  private Set<Authority> authorities;

  private boolean enabled;



  public User(String username, String email, String password, String phone) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(Role::getAuthorities)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }





}
