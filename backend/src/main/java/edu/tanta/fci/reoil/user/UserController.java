package edu.tanta.fci.reoil.user;

import edu.tanta.fci.reoil.user.model.NewAddress;
import edu.tanta.fci.reoil.user.model.Profile;
import edu.tanta.fci.reoil.user.model.ProfileUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
@Slf4j
public class UserController {

  private final UserService userService;
  @PatchMapping
  public ResponseEntity<?> update(@RequestBody @Valid ProfileUpdate profileUpdate, Authentication authentication) {
    userService.updateProfile(profileUpdate, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<Profile> getProfile(Authentication authentication) {
    final Profile profileByUsername = userService.findProfileByUsername(authentication.getName());
    log.info("Get profile {}", profileByUsername);
    return ResponseEntity.ok(profileByUsername);
  }

  @GetMapping("addresses")
  public ResponseEntity<?> getAddresses(Authentication authentication) {
    return ResponseEntity.ok(userService.findAddressesByUsername(authentication.getName()));
  }

  @PostMapping("addresses")
  public ResponseEntity<?> addAddress(@RequestBody @Valid NewAddress address, Authentication authentication) {
    userService.addAddress(address, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("addresses/{id}")
  public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id, Authentication authentication) {
    return userService.deleteAddressById(id, authentication.getName());
  }



}
