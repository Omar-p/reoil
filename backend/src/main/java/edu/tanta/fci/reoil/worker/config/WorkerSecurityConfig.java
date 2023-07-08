package edu.tanta.fci.reoil.worker.config;

import edu.tanta.fci.reoil.worker.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WorkerSecurityConfig {



  @Bean
  AuthenticationManager workerAuthenticationManager(PasswordEncoder passwordEncoder, WorkerRepository workerRepository) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(
        email -> workerRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User with email %s not found".formatted(email))
        )
    );

    return new ProviderManager(daoAuthenticationProvider);
  }

}
