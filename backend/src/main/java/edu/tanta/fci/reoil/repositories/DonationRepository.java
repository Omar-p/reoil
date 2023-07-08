package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.domain.Donation;
import edu.tanta.fci.reoil.model.DonationDTO;
import edu.tanta.fci.reoil.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface DonationRepository
    extends JpaRepository<Donation, Long> {

  @Query("select new edu.tanta.fci.reoil.model.DonationDTO(" +
      "d.charity.id, " +
      "d.charity.name, " +
      "d.program.name, " +
      "d.amount, " +
      "d.createdAt, " +
      "d.charity.imageUriId" +
      ") " +
      "from Donation d " +
      "where d.user = ?1 " +
      "order by d.createdAt desc")
  List<DonationDTO> findByUser(User user);
}
