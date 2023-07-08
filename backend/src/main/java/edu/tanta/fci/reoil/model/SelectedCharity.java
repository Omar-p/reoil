package edu.tanta.fci.reoil.model;

import java.util.List;

public record SelectedCharity(Long id, String name, Long currentPoints, Long numberOfDonors, String about, String site, String phone, String imageUri, List<String> programs) {

  public SelectedCharity {
    if (imageUri != null && !imageUri.isBlank()) {
      imageUri = "https://reoil-graduation-project.s3.us-east-1.amazonaws.com/charity-image/%d/%s".formatted(id, imageUri);
    }
    if (currentPoints == null) {
      currentPoints = 0L;
    }

    if (numberOfDonors == null) {
      numberOfDonors = 0L;
    }
  }
}
