package edu.tanta.fci.reoil.domain.security;

import edu.tanta.fci.reoil.user.entities.User;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

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

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(name = "role_authority",
      joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
  private Set<Authority> authorities = new HashSet<>();


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public void addAuthority(Authority authority) {
    this.authorities.add(authority);
  }

  public void addUsers(User user) {
    this.users.add(user);
  }
  public static final class Builder {
    private Long id;
    private String name;
    private Set<Authority> authorities;


    private Builder() {
    }

    public static Builder aRole() {
      return new Builder();
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder authorities(Set<Authority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public Role build() {
      Role role = new Role();
      role.setId(id);
      role.setName(name);
      role.setAuthorities(authorities);
      return role;
    }
  }
}