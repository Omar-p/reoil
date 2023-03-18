package edu.tanta.fci.reoil.password;

import edu.tanta.fci.reoil.password.model.ChangePassword;
import edu.tanta.fci.reoil.password.model.ResetPassword;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordController {

  private final PasswordService passwordService;

  private final ApplicationEventPublisher eventPublisher;

  public PasswordController(PasswordService passwordService, ApplicationEventPublisher eventPublisher) {
    this.passwordService = passwordService;
    this.eventPublisher = eventPublisher;
  }


  @PostMapping("/reset/code")
  public ResponseEntity<?> validateResetPasswordCode(@RequestParam int code) {
    return new ResponseEntity<>(
        passwordService.validateResetPasswordCode(code),
        HttpStatus.OK
    );
  }

  @GetMapping("/reset/code")
  public void validateResetPasswordCode(@RequestParam String email) {
    eventPublisher.publishEvent(new ForgetPasswordEvent(email));
  }

  @PostMapping("/reset")
  @PreAuthorize("hasAuthority('password:reset')")
  public ResponseEntity<?> resetPassword(Authentication authentication, @Valid @RequestBody ResetPassword resetPassword) {
    passwordService.resetPassword(authentication, resetPassword);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/change")
  public ResponseEntity<?> changePassword(Authentication authentication, @Valid @RequestBody ChangePassword changePassword) {
    passwordService.changePassword(authentication.getName(), changePassword);
    return ResponseEntity.ok().build();
  }
}
