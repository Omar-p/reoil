package edu.tanta.fci.reoil.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
public class S3Service {

  private final S3Client s3Client;

  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public void putObject(String bucketName, String key, byte[] file) {
    PutObjectRequest putObjectRequest = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
  }

  public byte[] getObject(String bucketName, String key) {
    GetObjectRequest getObjectRequest = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();

    final ResponseInputStream<GetObjectResponse> res = s3Client.getObject(getObjectRequest);

    try {
      return res.readAllBytes();
    } catch (Exception e) {
      log.error("Error reading bytes from S3. key: {}, bucket: {}", key, bucketName);
      throw new RuntimeException(e);
    }
  }
}
