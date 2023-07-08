package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT new edu.tanta.fci.reoil.user.model.Profile(u.fullName, u.email, u.phoneNumber, u.points, u.usedPoints, concat(u.id, '/', u.imageId) ) FROM User u WHERE u.username = :username")
    Profile findProfileByUsername(@Param("username") String username);


}
