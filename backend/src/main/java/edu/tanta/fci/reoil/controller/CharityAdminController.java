package edu.tanta.fci.reoil.controller;

import edu.tanta.fci.reoil.model.NewCharity;
import edu.tanta.fci.reoil.service.CharityAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/charities")

public class CharityAdminController {

  private final CharityAdminService charityAdminService;

  public CharityAdminController(CharityAdminService charityAdminService) {
    this.charityAdminService = charityAdminService;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<?> addCharity(@Valid @RequestBody NewCharity newCharity) {
    Long charityId = charityAdminService.addCharity(newCharity);
    return ResponseEntity.created(URI.create("/api/v1/charities/" + charityId)).build();
  }

  @PostMapping("/{charityId}/{programName}")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public void addProgram(@PathVariable long charityId, @PathVariable String programName) {
    charityAdminService.addProgram(charityId, programName);
  }

  @DeleteMapping("/{charityId}/{programName}")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public void deleteProgram(@PathVariable long charityId, @PathVariable String programName) {
    charityAdminService.deleteProgram(charityId, programName);
  }

}
