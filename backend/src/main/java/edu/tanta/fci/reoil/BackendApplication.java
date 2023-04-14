package edu.tanta.fci.reoil;

import edu.tanta.fci.reoil.config.RsaKeyProperties;
import edu.tanta.fci.reoil.domain.Charity;
import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.domain.security.Authority;
import edu.tanta.fci.reoil.domain.security.Role;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import edu.tanta.fci.reoil.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}





}
