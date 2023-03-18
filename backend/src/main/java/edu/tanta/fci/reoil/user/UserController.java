package edu.tanta.fci.reoil.user;

import edu.tanta.fci.reoil.user.model.NewAddress;
import edu.tanta.fci.reoil.user.model.Profile;
import edu.tanta.fci.reoil.user.model.ProfileUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

  private final UserService userService;
  @PatchMapping
  public ResponseEntity<?> update(@RequestBody @Valid ProfileUpdate profileUpdate, Authentication authentication) {
    userService.updateProfile(profileUpdate, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<Profile> getProfile(Authentication authentication) {
    return ResponseEntity.ok(userService.findProfileByUsername(authentication.getName()));
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
