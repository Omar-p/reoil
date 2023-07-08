package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.worker.requests.WorkerRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/worker/registration")
public class WorkerRegistrationController {

  private final WorkerRegistrationService workerRegistrationService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void register(@RequestPart("info") @Valid WorkerRegistrationRequest request,
                       @RequestPart("frontIdImage") MultipartFile frontIdImage,
                       @RequestPart("backIdImage")MultipartFile backIdImage,
                       @RequestPart("drivingLicenseImage")MultipartFile licenseImage) {
    log.info("register: {}", request);
    workerRegistrationService.registerWorker(request, frontIdImage, backIdImage, licenseImage);
  }
}
