package edu.tanta.fci.reoil.service;

import edu.tanta.fci.reoil.domain.Charity;
import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.exceptions.UnprocessableContentException;
import edu.tanta.fci.reoil.model.NewCharity;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import edu.tanta.fci.reoil.s3.S3Buckets;
import edu.tanta.fci.reoil.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class CharityAdminService {

  private final CharityRepository charityRepository;
  private final S3Service s3Service;
  private final S3Buckets s3Buckets;

  public CharityAdminService(CharityRepository charityRepository, S3Service s3Service, S3Buckets s3Buckets) {
    this.charityRepository = charityRepository;
    this.s3Service = s3Service;
    this.s3Buckets = s3Buckets;
  }

  public Long addCharity(NewCharity newCharity) {
    charityRepository.findByName(newCharity.getName())
        .ifPresent(charity -> {
          throw new UnprocessableContentException("Charity with name " + newCharity.getName() + " already exists");
        });

    var charity = new Charity(
        newCharity.getName(),
       newCharity.getDescription(),
        newCharity.getAbout(),
        newCharity.getSite(),
        newCharity.getPhone()
    );

    newCharity.getPrograms()
        .stream()
        .distinct()
        .map(Program::new)
        .forEach(charity::addProgram);


    charityRepository.save(charity);
    return charity.getId();
  }


  public void addProgram(long charityId, String programName) {
    charityRepository.findById(charityId).ifPresentOrElse(charity -> {
      var program = new Program(programName);
      log.info("Program: {}", program);
      final Optional<String> any = charity.getPrograms().stream()
          .map(Program::getName)
          .filter(name -> name.equals(programName))
          .findAny();
      if (any.isPresent()) {
        throw new UnprocessableContentException("Program with name " + programName + " already exists");
      }
      log.info("reach");
      charity.addProgram(program);

      charityRepository.save(charity);
    }, () -> {
      throw new NotFoundException("Charity with id " + charityId + " not found");
    });
  }

  public void deleteProgram(long charityId, String programName) {
    int rowAffected = this.charityRepository.deleteProgram(charityId, programName);
    if (rowAffected == 0) {
      throw new NotFoundException("No program with name " + programName + " found for charity with id " + charityId);
    }
  }

  public String addCharityImage(long charityId, MultipartFile image) {
    var charity = charityRepository.findById(charityId)
        .orElseThrow(() -> new NotFoundException("Charity with id " + charityId + " not found"));

    final String imageId = UUID.randomUUID().toString();
    try {
      s3Service.putObject(
          s3Buckets.getReoil(),
          "charity-image/%s/%s".formatted(charity.getId().toString(), imageId),
          image.getBytes()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    charity.setImageUriId(imageId);

    return "https://%s.s3.%s.amazonaws.com/charity-image/%s/%s"
        .formatted(s3Buckets.getReoil(), s3Buckets.getRegion(), charity.getId().toString(), imageId);
  }
}
