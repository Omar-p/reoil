package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.user.model.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/worker/auth")
@RequiredArgsConstructor
public class WorkerAuthController {

  private final WorkerAuthenticationService workerAuthenticationService;



  @PostMapping
  public ResponseEntity<?> authenticate(@RequestBody Login login) {
    return new ResponseEntity<>(workerAuthenticationService.authenticate(login), HttpStatus.OK);
  }
}
