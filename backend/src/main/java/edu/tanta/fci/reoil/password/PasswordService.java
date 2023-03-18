package edu.tanta.fci.reoil.password;

import edu.tanta.fci.reoil.exceptions.BadRequestException;
import edu.tanta.fci.reoil.password.model.ChangePassword;
import edu.tanta.fci.reoil.password.model.ResetPassword;
import edu.tanta.fci.reoil.service.AccessTokenService;
import edu.tanta.fci.reoil.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PasswordService {

  private final ResetPasswordCodeRepository resetPasswordCodeRepository;
  private final UserService userService;


  private final AccessTokenService accessTokenService;

  public PasswordService(ResetPasswordCodeRepository resetPasswordCodeRepository, UserService userService, AccessTokenService accessTokenService) {
    this.resetPasswordCodeRepository = resetPasswordCodeRepository;
    this.userService = userService;
    this.accessTokenService = accessTokenService;
  }

  public Map<String, String> validateResetPasswordCode(int code) {
    var resetPasswordCode = resetPasswordCodeRepository.findByCode(code)
        .orElseThrow(() -> new BadRequestException("Invalid code"));

    if (resetPasswordCode.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new BadRequestException("Code expired");
    }

    return Map.of("access_token", accessTokenService.generateTokenForResettingPassword(resetPasswordCode.getEmail()));
  }

  public void resetPassword(Authentication authentication, ResetPassword resetPassword) {

    var email = authentication.getName();

    userService.resetPassword(resetPassword.password(), email);

  }

  public void changePassword(String username, ChangePassword changePassword) {
    userService.changePassword(username, changePassword);
  }

  public int createResetPasswordCode(String email) {

    var user = userService.findByEmail(email);

    var resetPasswordCode = new ResetPasswordCode(email);

    resetPasswordCodeRepository.save(resetPasswordCode);

    return resetPasswordCode.getCode();
  }
}
