package edu.tanta.fci.reoil.user.registration;

import edu.tanta.fci.reoil.user.entities.Verification;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationService {

  private final VerificationCodeRepository repository;

  public VerificationService(VerificationCodeRepository repository) {
    this.repository = repository;
  }

  public String getVerificationIdByUsername(String username) {
    return repository
        .findIdByUsername(username)
        .orElseThrow(() -> new NotFoundException("Email verification code not found"));
  }

  public String createVerification(String username) {
    if (!repository.existsByUsername(username)) {
      Verification verification = new Verification(username);
      verification = repository.save(verification);
      return verification.getId();
    }
    return getVerificationIdByUsername(username);
  }

  public String getUsernameForId(String id) {
    Optional<Verification> verification = repository.findById(id);
    return verification.map(Verification::getUsername)
        .orElseThrow(() -> new NotFoundException("Verification code not found"));
  }

  public void deleteVerification(String token) {
    repository.deleteById(token);
  }
}
