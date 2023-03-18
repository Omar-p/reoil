package edu.tanta.fci.reoil.user.registration;

import edu.tanta.fci.reoil.user.model.RegistrationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
class RegistrationController {


  private final Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);
  private final RegistrationService registrationService;

  RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<?> register( @Valid @RequestBody RegistrationRequest request) {
    logger.info("{}", request);
    registrationService.processUserRegistration(request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<?> verify(@RequestParam("token") String token) {
    logger.info("token {}", token);
    registrationService.verifyUser(token);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
