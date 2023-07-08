package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.user.model.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkerAuthenticationService {

  private final AuthenticationManager workerAuthenticationManager;

  private final WorkerRepository workerRepository;

  private final JwtEncoder jwtEncoder;


  private String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(24L * 100L, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("scope", scope)
        .claim("id", workerRepository.findByEmail(authentication.getName()).get().getId())
        .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public Map<String, String> authenticate(Login login) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
    final Authentication authentication = workerAuthenticationManager.authenticate(authenticationToken);
    var accessToken = this.generateToken(authentication);
    return Map.of("accessToken", accessToken);
  }
}
