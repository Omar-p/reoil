package edu.tanta.fci.reoil;

import edu.tanta.fci.reoil.config.RsaKeyProperties;
import edu.tanta.fci.reoil.domain.security.Authority;
import edu.tanta.fci.reoil.domain.security.Role;
import edu.tanta.fci.reoil.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	@Bean
	CommandLineRunner loadder(RoleRepository roleRepository) {
		return (args) -> {
			if (roleRepository.count() == 0) {
				final Authority userRead = Authority.Builder.anAuthority()
						.withPermission("user.info.read")
						.build();

				final Role userRole = Role.Builder.aRole()
						.authorities(Set.of(userRead))
						.name("ROLE_USER")
						.build();

				roleRepository.save(userRole);
			}
		};
	}


}
