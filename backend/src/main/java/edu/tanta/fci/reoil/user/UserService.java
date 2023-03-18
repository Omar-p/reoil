package edu.tanta.fci.reoil.user;

import edu.tanta.fci.reoil.exceptions.EmailIsAlreadyExist;
import edu.tanta.fci.reoil.exceptions.NewPasswordEqualOldPasswordException;
import edu.tanta.fci.reoil.exceptions.WrongPasswordException;
import edu.tanta.fci.reoil.password.model.ChangePassword;
import edu.tanta.fci.reoil.repositories.AddressRepository;
import edu.tanta.fci.reoil.repositories.RoleRepository;
import edu.tanta.fci.reoil.repositories.UserRepository;
import edu.tanta.fci.reoil.user.entities.Address;
import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.user.model.AddressResponse;
import edu.tanta.fci.reoil.user.model.NewAddress;
import edu.tanta.fci.reoil.user.model.Profile;
import edu.tanta.fci.reoil.user.model.ProfileUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final AddressRepository addressRepository;

  public void resetPassword(String newPassword, String email) {
    final User user = findByEmail(email);
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new NewPasswordEqualOldPasswordException("new password must be different from the old one");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  public void changePassword(String username, ChangePassword changePassword) {
    final User user = findByUsername(username);

    if (!passwordEncoder.matches(changePassword.oldPassword(), user.getPassword())) {
      throw new WrongPasswordException("old password is not correct");
    }

    if (passwordEncoder.matches(changePassword.password(), user.getPassword())) {
      throw new NewPasswordEqualOldPasswordException("new password must be different from the old one");
    }

    user.setPassword(passwordEncoder.encode(changePassword.password()));
    userRepository.save(user);
  }



  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final Optional<User> user = userRepository.findByEmail(email);
    return user.orElseThrow(() -> new UsernameNotFoundException("no account match this email: " + email));
  }

  public User createUser(User user) {
    user.addRole(roleRepository.findByName("ROLE_USER").get());
    return userRepository.save(user);
  }

  public void enableUser(String username) {
    userRepository.findByUsername(username)
        .ifPresent(user -> {
          user.setEnabled(true);
          userRepository.save(user);
        });
  }

  public boolean isUserExistByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("no account match this email: " + email));
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("no account match this username: " + username));
  }

  @Transactional
  void updateProfile(ProfileUpdate profileUpdate, String username) {
    var user = findByUsername(username);
   if (profileUpdate.fullName() != null) {
      user.setFullName(profileUpdate.fullName());
    }
    if (profileUpdate.phoneNumber() != null) {
      user.setPhoneNumber(profileUpdate.phoneNumber());
    }
    if (profileUpdate.email() != null) {
      userRepository.findByEmail(profileUpdate.email())
          .ifPresent(u -> {
            throw new EmailIsAlreadyExist(profileUpdate.email() + " already exist");
          });
      user.setEmail(profileUpdate.email());
    }
    userRepository.save(user);

  }


  Profile findProfileByUsername(String username) {
    return userRepository.findProfileByUsername(username);
  }

  @Transactional
  List<AddressResponse> findAddressesByUsername(String name) {
    var user = findByUsername(name);
    return addressRepository.findByUser(user);
  }

  @Transactional
  void addAddress(NewAddress address, String name) {
    var user = findByUsername(name);
    user.addAddress(toAddress(address));
    userRepository.save(user);
  }



  @Transactional
  public ResponseEntity<?> deleteAddressById(Long id, String name) {
    var user = findByUsername(name);
    if (user.getAddresses()
        .stream()
        .map(Address::getId)
        .anyMatch(id::equals)) {

      user.removeAddress(addressRepository.findById(id).get());

      return ResponseEntity.ok().build();
    }

    return ResponseEntity.notFound().build();
  }

  private Address toAddress(NewAddress address) {
    return new Address(
        address.title(),
        address.address(),
        address.isMain(),
        address.firstName() + " " + address.lastName(),
        address.phoneNumber()
    );
  }
}
