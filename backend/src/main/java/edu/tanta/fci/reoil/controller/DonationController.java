package edu.tanta.fci.reoil.controller;

import edu.tanta.fci.reoil.model.DonationRequest;
import edu.tanta.fci.reoil.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

  private final DonationService donationService;

  @PostMapping
  public void donate(@RequestBody DonationRequest request, Authentication authentication) {
    donationService.donate(request, authentication);
  }
}
