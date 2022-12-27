package com.example.reoil.user;

import com.example.reoil.domain.VerificationToken;
import com.example.reoil.domain.security.Authority;
import com.example.reoil.domain.security.Role;
import com.example.reoil.domain.security.User;
import com.example.reoil.repositories.RoleRepository;
import com.example.reoil.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService  {

  private final UserRepository userRepository;
  private final VerificationTokenRepository tokenRepository;
  private final RoleRepository roleRepository;

  public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.tokenRepository = verificationTokenRepository;
    this.roleRepository = roleRepository;
  }


  public Optional<User> findByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  public Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public User registerNewUserAccount(User user) {
    user.setRoles(new HashSet<>(Set.of(roleRepository.findByName("ROLE_USER"))));
    return userRepository.save(user);
  }

  public Optional<VerificationToken> getVerificationToken(String VerificationToken) {
    return tokenRepository.findByToken(VerificationToken);
  }

  public void createVerificationToken(User user, String token) {
    VerificationToken myToken = new VerificationToken(token, user);
    tokenRepository.save(myToken);
  }

  public void saveRegisteredUser(User user) {
    userRepository.save(user);
  }
}
