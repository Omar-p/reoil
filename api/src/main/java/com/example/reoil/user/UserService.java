package com.example.reoil.user;

import com.example.reoil.model.VerificationToken;
import com.example.reoil.model.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService  {

  private final UserRepository userRepository;
  private final VerificationTokenRepository tokenRepository;

  public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
    this.userRepository = userRepository;
    this.tokenRepository = verificationTokenRepository;
  }


  public Optional<User> findByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  public Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public User registerNewUserAccount(User user) {
    user.setRole(Role.USER);
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
