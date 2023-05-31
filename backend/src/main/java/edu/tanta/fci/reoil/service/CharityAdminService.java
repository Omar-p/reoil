package edu.tanta.fci.reoil.service;

import edu.tanta.fci.reoil.domain.Charity;
import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.model.NewCharity;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CharityAdminService {

  private final CharityRepository charityRepository;

  public CharityAdminService(CharityRepository charityRepository) {
    this.charityRepository = charityRepository;
  }

  public Long addCharity(NewCharity newCharity) {
    var charity = new Charity(
        newCharity.getName(),
       newCharity.getDescription(),
        newCharity.getAbout(),
        newCharity.getSite(),
        newCharity.getPhone()
    );

    newCharity.getPrograms().stream().map(Program::new).forEach(charity::addProgram);

    charityRepository.save(charity);
    return charity.getId();
  }


  public void addProgram(long charityId, String programName) {
    charityRepository.findById(charityId).ifPresentOrElse(charity -> {
      var program = new Program(programName);
      charity.addProgram(program);
      charityRepository.save(charity);
    }, () -> {
      throw new NotFoundException("Charity with id " + charityId + " not found");
    });
  }

  public void deleteProgram(long charityId, String programName) {
    int rowAffected = this.charityRepository.deleteProgram(charityId, programName);
    if (rowAffected == 0) {
      throw new NotFoundException("No program with name " + programName + " found for charity with id " + charityId);
    }
  }
}
