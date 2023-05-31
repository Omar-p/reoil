package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

  Cart findCartByUser(User user);


}
