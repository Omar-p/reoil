package edu.tanta.fci.reoil.service;

import edu.tanta.fci.reoil.domain.Charity;
import edu.tanta.fci.reoil.domain.Donation;
import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.model.DonationRequest;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import edu.tanta.fci.reoil.repositories.DonationRepository;
import edu.tanta.fci.reoil.user.UserService;
import edu.tanta.fci.reoil.user.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DonationService {

  private final CharityService charityService;

  private final DonationRepository donationRepository;

  private final UserService userService;

  public void donate(DonationRequest donationRequest, Authentication authentication) {
    var charity = charityService.getCharityById(donationRequest.charityId());
    final Program program = charity.getPrograms()
        .stream()
        .filter(p -> p.getName().equals(donationRequest.programName()))
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Program not found"));

    final User user = userService.findByUsername(authentication.getName());
    userService.usePoints(user, donationRequest.points());

    donationRepository.save(new Donation(user, charity, program, donationRequest.points()));
  }

}
