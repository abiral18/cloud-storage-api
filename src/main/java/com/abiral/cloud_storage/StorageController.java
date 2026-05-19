package com.abiral.cloud_storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class StorageController {

    private final S3Client s3;
    private final String bucketName = "AWS_BUCKET_NAME";

    public StorageController() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            "AWS_ACCESS_KEY",
            "AWS_SECRET_KEY"
        );
        this.s3 = S3Client.builder()
            .region(Region.US_EAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        s3.putObject(
            PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
            software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
        );
        return ResponseEntity.ok("Uploaded: " + fileName);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        List<String> files = s3.listObjectsV2(
            ListObjectsV2Request.builder().bucket(bucketName).build()
        ).contents().stream().map(S3Object::key).collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        s3.deleteObject(
            DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build()
        );
        return ResponseEntity.ok("Deleted: " + fileName);
    }
}