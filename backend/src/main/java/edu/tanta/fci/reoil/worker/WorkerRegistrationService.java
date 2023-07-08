package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.domain.Worker;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.repositories.RoleRepository;
import edu.tanta.fci.reoil.s3.S3Buckets;
import edu.tanta.fci.reoil.s3.S3Service;
import edu.tanta.fci.reoil.worker.requests.WorkerRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class WorkerRegistrationService {

  private final WorkerRepository workerRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final RoleRepository roleRepository;
  private final S3Service s3Service;
  private final S3Buckets s3Buckets;
  private final PasswordEncoder passwordEncoder;

  private static final String IMAGE_PATH = "worker/%s/%s";
  public void registerWorker(WorkerRegistrationRequest request,
                             MultipartFile frontIdImage,
                             MultipartFile backIdImage,
                             MultipartFile licenseImage) {

    Worker worker = new Worker();
    worker.setName(request.name());
    worker.setEmail(request.email());
    worker.setPassword(passwordEncoder.encode(request.password()));
    worker.setPhoneNumber(request.phone());
    worker.setCreatedAt(LocalDateTime.now());

    worker = workerRepository.save(worker);

    UUID frontIdImageName = UUID.randomUUID();
    UUID backIdImageName = UUID.randomUUID();
    UUID licenseImageName = UUID.randomUUID();


    try {
      s3Service.putObject(
          s3Buckets.getReoil(),
          IMAGE_PATH.formatted(worker.getId().toString(), frontIdImageName),
          frontIdImage.getBytes()
      );

      s3Service.putObject(
          s3Buckets.getReoil(),
          IMAGE_PATH.formatted(worker.getId().toString(), backIdImageName),
          backIdImage.getBytes()
      );

      s3Service.putObject(
          s3Buckets.getReoil(),
          IMAGE_PATH.formatted(worker.getId().toString(), licenseImageName),
          licenseImage.getBytes()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    worker.setFrontIdImageId(frontIdImageName.toString());
    worker.setBackIdImageId(backIdImageName.toString());
    worker.setDrivingLicenseImageId(licenseImageName.toString());

    worker.addRole(roleRepository.findByName("ROLE_WORKER").orElseThrow(
        () -> new NotFoundException("Role ROLE_WORKER not found")
    ));

    workerRepository.save(worker);

    eventPublisher.publishEvent(new WorkerRegisteredEvent(worker));
  }
}
