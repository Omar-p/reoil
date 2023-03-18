package edu.tanta.fci.reoil.domain.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

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


  public static final class Builder {
    private Long id;
    private String permission;
    private Set<Role> roles;

    private Builder() {
    }

    public static Builder anAuthority() {
      return new Builder();
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withPermission(String permission) {
      this.permission = permission;
      return this;
    }

    public Builder withRoles(Set<Role> roles) {
      this.roles = roles;
      return this;
    }

    public Authority build() {
      Authority authority = new Authority();
      authority.roles = this.roles;
      authority.id = this.id;
      authority.permission = this.permission;
      return authority;
    }
  }
}