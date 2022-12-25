package com.example.reoil.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @SequenceGenerator(
      name = "users_id_generator",
      initialValue = 1,
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

  @Enumerated(EnumType.STRING)
  private Role role;

  private boolean enabled;

  public User() {
  }

  public User(String username, String email, String password, String phone) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getGrantedAuthorities();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
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

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  public void setRole(Role role) {
    this.role = role;
  }

  public Role getRole() {
    return role;
  }
}
