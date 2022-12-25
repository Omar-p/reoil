package com.example.reoil.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum Role {
  ADMIN,
  USER;

  public Set<? extends GrantedAuthority> getGrantedAuthorities() {
    return Set.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
  }
}
