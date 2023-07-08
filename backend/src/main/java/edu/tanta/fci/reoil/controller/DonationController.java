package edu.tanta.fci.reoil.controller;

import edu.tanta.fci.reoil.model.DonationDTO;
import edu.tanta.fci.reoil.model.DonationRequest;
import edu.tanta.fci.reoil.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

  private final DonationService donationService;

  @PostMapping
  @PreAuthorize("hasAuthority('SCOPE_USER')")
  public void donate(@RequestBody DonationRequest request, Authentication authentication) {
    donationService.donate(request, authentication);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('SCOPE_USER')")
  public Map<String, List<DonationDTO>> getUserDonation(Authentication authentication) {
    return Map.of("donations", donationService.getUserDonation(authentication));
  }
}
