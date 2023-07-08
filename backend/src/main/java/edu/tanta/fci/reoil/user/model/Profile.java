package edu.tanta.fci.reoil.user.model;

public record Profile(
    String fullName,
    String email,
    String phoneNumber,
    Long points,
    Long usedPoints,

    String imageUri
) {
  public Profile {
    if (imageUri != null && !imageUri.isBlank())
      imageUri = "https://reoil-graduation-project.s3.us-east-1.amazonaws.com/profile-image/%s".formatted(imageUri);
  }
}
