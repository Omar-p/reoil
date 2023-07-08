package edu.tanta.fci.reoil.user.image;

import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.s3.S3Buckets;
import edu.tanta.fci.reoil.s3.S3Service;
import edu.tanta.fci.reoil.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService {


  private final S3Service s3Service;
  private final S3Buckets s3Buckets;
  private final UserService userService;

  @Transactional
  public String uploadUserProfileImage(String username,
                                         MultipartFile file) {

    var user = userService.findByUsername(username);
    final String profileImageId = UUID.randomUUID().toString();
    try {
      s3Service.putObject(
          s3Buckets.getReoil(),
          "profile-image/%s/%s".formatted(user.getId().toString(), profileImageId),
          file.getBytes()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    user.setImageId(profileImageId);

    return "https://%s.s3.%s.amazonaws.com/profile-image/%s/%s"
        .formatted(s3Buckets.getReoil(), s3Buckets.getRegion(), user.getId().toString(), profileImageId);
  }

  public byte[] getUserProfileImage(String name) {
    var user = userService.findByUsername(name);
    if (user.getImageId() == null) {
      throw new NotFoundException("%s has no profile image".formatted(name));
    }
    return s3Service.getObject(
        s3Buckets.getReoil(),
        "profile-image/%s/%s".formatted(user.getId().toString(), user.getImageId())
    );
  }
}
