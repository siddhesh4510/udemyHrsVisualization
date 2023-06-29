package com.example.fileprocessing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fileprocessing.repo.AwsServiceRepository;



@RestController
public class FileController {
	@Value("${aws.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${aws.secretKey}")
    private String awsSecretKey;
    
    @Value("${s3.bucket.name}")
    private String s3BucketName;
    
//    @Autowired
//    AwsServiceRepository rpo;
    
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		try {
			BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretKey);
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withEndpointConfiguration(new EndpointConfiguration("s3.ap-south-1.amazonaws.com", "ap-south-1"))
                    .build();
            String fileName = file.getOriginalFilename();
            if(fileName.contains("xlsx")) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, "files/data.xlsx", file.getInputStream(), null);
           s3Client.putObject(putObjectRequest);
            return ResponseEntity.ok("File uploaded successfully");
            }else {
            	return ResponseEntity.ok("Upload xlsx file only");
            }
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());

		}

	}

}
