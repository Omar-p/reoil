package edu.tanta.fci.reoil.password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordCodeRepository extends JpaRepository<ResetPasswordCode, Long> {

  Optional<ResetPasswordCode> findByCode(int code);
}
