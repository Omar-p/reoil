package edu.tanta.fci.reoil.admin;

import edu.tanta.fci.reoil.model.NewCharity;
import edu.tanta.fci.reoil.service.CharityAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;

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

  @PostMapping(value = "/{charityId}", consumes = "multipart/form-data")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public Map<String, String> addCharityImage(@PathVariable("charityId") long charityId, @RequestParam("image") MultipartFile image) {
    return Map.of("image_uri", charityAdminService.addCharityImage(charityId, image));
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
