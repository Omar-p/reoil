package edu.tanta.fci.reoil.user.entities;

import edu.tanta.fci.reoil.domain.security.Authority;
import edu.tanta.fci.reoil.domain.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "users_email_key", columnNames = "email"),
        @UniqueConstraint(name = "users_username_key", columnNames = "username")
    }
)
public class User implements UserDetails {

  @Id
  @SequenceGenerator(
      name = "users_id_generator",
      allocationSize = 1,
      sequenceName = "users_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "users_id_generator"
  )
  private Long id;

  private String fullName;

  private String username;

  private String email;

  private String password;

  private String phoneNumber;

  private String imageId;

  private long points;

  private long usedPoints;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
  private Set<Role> roles = new HashSet<>();

  @CreationTimestamp
  private LocalDateTime createdAt;

  private boolean enabled;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
  private List<Address> addresses = new ArrayList<>();

  @Version
  private int version;

  @Transient
  private Set<Authority> authorities;

  public User(String username, String email, String password, String phoneNumber, boolean enabled) {
    this.username = username;
    this.fullName = username;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.enabled = enabled;
  }



  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(Role::getAuthorities)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }


  public void addAddress(Address address) {
    addresses.add(address);
    address.setUser(this);
  }

  public void removeAddress(Address address) {
    addresses.remove(address);
    address.setUser(null);
  }


  public void addRole(Role role) {
    this.roles.add(role);
  }


}
