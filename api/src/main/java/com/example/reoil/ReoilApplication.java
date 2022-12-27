package com.example.reoil;

import com.example.reoil.domain.security.Authority;
import com.example.reoil.domain.security.Role;
import com.example.reoil.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class ReoilApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReoilApplication.class, args);
	}
	@Bean
	CommandLineRunner loadder(RoleRepository roleRepository) {
		return (args) -> {
			final Authority userRead = Authority.builder()
					.permission("user.info.read")
					.build();

			final Role userRole = Role.builder()
					.authorities(Set.of(userRead))
					.name("ROLE_USER")
					.build();

			roleRepository.save(userRole);
		};
	}
}
