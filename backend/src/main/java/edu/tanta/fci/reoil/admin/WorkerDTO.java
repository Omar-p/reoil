package edu.tanta.fci.reoil.admin;

import java.time.LocalDateTime;

public record WorkerDTO(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String frontIdImageUrl,
    String backIdImageUrl,
    String drivingLicenseImageUrl,
    LocalDateTime createdAt
) {

  private static final String BASE_URL = "https://reoil-graduation-project.s3.us-east-1.amazonaws.com/worker/%s/%s";
  public WorkerDTO {

    if (frontIdImageUrl != null && !frontIdImageUrl.isBlank())
      frontIdImageUrl = BASE_URL.formatted(id, frontIdImageUrl);

    if (backIdImageUrl != null && !backIdImageUrl.isBlank())
      backIdImageUrl = BASE_URL.formatted(id, backIdImageUrl);

    if (drivingLicenseImageUrl != null && !drivingLicenseImageUrl.isBlank())
      drivingLicenseImageUrl = BASE_URL.formatted(id, drivingLicenseImageUrl);

  }
}
