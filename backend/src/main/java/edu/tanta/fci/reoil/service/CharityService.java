package edu.tanta.fci.reoil.service;

import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.model.Charity;
import edu.tanta.fci.reoil.model.SelectedCharity;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CharityService {


  private final CharityRepository charityRepository;
  public List<Charity> getCharities() {
    return charityRepository.getCharities();
  }

  public SelectedCharity getCharity(long id) {
    var charity = charityRepository.findById(id).orElseThrow(
        () -> new NotFoundException("Charity with id " + id + " not found")
    );

    return new SelectedCharity(
        charity.getId(),
        charity.getName(),
        charity.getPoints(),
        charity.getNumberOfDonors(),
        charity.getAbout(),
        charity.getSite(),
        charity.getPhone(),
        charity.getPrograms()
            .stream()
            .map(Program::getName)
            .toList()
    );
  }

  public edu.tanta.fci.reoil.domain.Charity getCharityById(long charityId) {
    return charityRepository.findById(charityId).orElseThrow(
        () -> new NotFoundException("Charity with id " + charityId + " not found")
    );
  }
}
