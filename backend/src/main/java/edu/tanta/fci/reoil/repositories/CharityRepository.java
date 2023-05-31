package edu.tanta.fci.reoil.repositories;

import edu.tanta.fci.reoil.domain.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CharityRepository
    extends JpaRepository<Charity, Long> {


  @Query("select new edu.tanta.fci.reoil.model.Charity(c.id, c.name, c.description, c.points) from Charity c")
  List<edu.tanta.fci.reoil.model.Charity> getCharities();

  @Modifying
  @Query("delete from Program p where p.charity.id = ?1 and p.name = ?2")
  int deleteProgram(long charityId, String programName);

}
