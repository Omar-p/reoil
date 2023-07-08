package edu.tanta.fci.reoil.model;

public record Charity(Long id, String name, String description, Long currentPoints, String imageUri) {
  public Charity {
    if (imageUri != null && !imageUri.isBlank()) {
      imageUri = "https://reoil-graduation-project.s3.us-east-1.amazonaws.com/charity-image/%d/%s".formatted(id, imageUri);
    }

    if (currentPoints == null) {
      currentPoints = 0L;
    }
  }
}
