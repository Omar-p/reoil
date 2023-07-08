package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.admin.WorkerDTO;
import edu.tanta.fci.reoil.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>  {
  Optional<Worker> findByEmail(String email);

  @Query(value = """
      SELECT new edu.tanta.fci.reoil.admin.WorkerDTO(
          w.id,
          w.name,
          w.email,
          w.phoneNumber,
          w.frontIdImageId,
          w.backIdImageId,
          w.drivingLicenseImageId,
          w.createdAt )     
       FROM Worker w 
      WHERE w.enabled = :enabled 
      """)
  Iterable<WorkerDTO> findAllByEnabledIs(boolean enabled);
}
