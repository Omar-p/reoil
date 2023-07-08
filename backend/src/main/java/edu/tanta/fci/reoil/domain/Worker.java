package edu.tanta.fci.reoil.domain;

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

@Entity
@Table(name = "workers",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "email", name = "worker_email_key")
  })
@Getter
@Setter
@NoArgsConstructor
public class Worker implements UserDetails {
  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "worker_id_generator"
  )
  @SequenceGenerator(
    name = "worker_id_generator",
    sequenceName = "worker_id_seq",
    allocationSize = 1
  )
  private Long id;

  private String email;

  private String password;

  private String name;

  private String frontIdImageId;

  private String backIdImageId;

  private String drivingLicenseImageId;


  private String phoneNumber;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private boolean enabled;



  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(name = "worker_role",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
  private Set<Role> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(Role::getAuthorities)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }

  @Override
  public String getUsername() {
    return email;
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
  public boolean isEnabled() {
    return enabled;
  }


  public void addRole(Role role) {
    this.roles.add(role);
  }


}
