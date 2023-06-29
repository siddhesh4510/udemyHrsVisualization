package com.example.fileprocessing.config;


import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.example.fileprocessing.repo.AwsServiceRepository;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.contactsunny.poc.DynamoDBSpringBootPOC.repositories")
public class DynamoDBConfig {
//	@Value("${amazon.dynamodb.endpoint}")
//    private String dynamoDbEndpoint;

    @Value("${aws.accessKeyId}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;
//    @Bean
//    public AwsServiceRepository awsServiceRepository() {
//        return new AwsServiceRepository();
//    }

	@Bean
    public AmazonDynamoDB amazonDynamoDB() {
    	AmazonDynamoDB amazonDynamoDB=AmazonDynamoDBClientBuilder.standard().
    								withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials())).
    								withEndpointConfiguration(new EndpointConfiguration("https://dynamodb.ap-south-1.amazonaws.com","ap-south-1")).
    								build();
//    	amazonDynamoDB.setEndpoint(dynamoDbEndpoint);
//        @SuppressWarnings("deprecation")
//		AmazonDynamoDB amazonDynamoDB
//                = new AmazonDynamoDBClient(amazonAWSCredentials());
//
//        if (!StringUtils.isEmpty(dynamoDbEndpoint)) {
//            amazonDynamoDB.setEndpoint(dynamoDbEndpoint);
//        }

        return amazonDynamoDB;
    }

    @Bean
     AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

}
