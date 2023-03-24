package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository
    extends JpaRepository<Donation, Long> {

}
