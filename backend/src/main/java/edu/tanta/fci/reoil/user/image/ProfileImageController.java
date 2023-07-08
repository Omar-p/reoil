package edu.tanta.fci.reoil.user.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/profile-image")
public class ProfileImageController {


  private final ProfileImageService profileImageService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> uploadUserProfileImage(Authentication authentication,
                                                  @RequestParam("file") MultipartFile file) {
    log.info("uploadUserProfileImage: {}", file.getOriginalFilename());
    log.info("uploadUserProfileImage: {}", file.getResource().isFile());
    final String profileImageUri = profileImageService.uploadUserProfileImage(authentication.getName(), file);
    return ResponseEntity.ok(Map.of("profileImageUri", profileImageUri));
  }

  @GetMapping
  public byte[] getUserProfileImage(Authentication authentication) {
    return profileImageService.getUserProfileImage(authentication.getName());
  }
}
