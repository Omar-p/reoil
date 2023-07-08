package edu.tanta.fci.reoil.model;

import java.time.LocalDateTime;

public record DonationDTO(
    Long charityId,

    String charityName,
    String programName,
    Long points,
    LocalDateTime createdAt,

    String charityImageUri
) {
  public DonationDTO {
    if (charityImageUri != null && !charityImageUri.isBlank()) {
      charityImageUri = "https://reoil-graduation-project.s3.us-east-1.amazonaws.com/charity-image/%d/%s".formatted(charityId, charityImageUri);
    }
  }
}
