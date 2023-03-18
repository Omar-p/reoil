package edu.tanta.fci.reoil.user.registration;

import edu.tanta.fci.reoil.user.entities.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<Verification, String> {

  Optional<Verification> findByUsername(String username);

  boolean existsByUsername(String username);

  @Query(value = "SELECT v.id FROM Verification v WHERE v.username = :username LIMIT 1", nativeQuery = true)
  Optional<String> findIdByUsername(@Param("username") String username);
}
