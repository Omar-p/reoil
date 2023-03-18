package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.user.entities.Address;
import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.user.model.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query("SELECT new edu.tanta.fci.reoil.user.model.AddressResponse(a.id, a.title, a.address, a.fullName, a.phoneNumber, a.main) FROM Address a WHERE a.user = :user")
  List<AddressResponse> findByUser(User user);


}
