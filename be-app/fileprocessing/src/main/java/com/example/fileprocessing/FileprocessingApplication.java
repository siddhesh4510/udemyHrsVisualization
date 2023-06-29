package com.example.fileprocessing;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.fileprocessing.entity.CourseInfo;
import com.example.fileprocessing.entity.RelationalCourseInfo;
import com.example.fileprocessing.jpa.repo.RelationalCourseRepository;
import com.example.fileprocessing.repo.AwsServiceRepository;
import com.example.fileprocessing.service.FetchDyanamoDBdata;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.example.fileprocessing.entity.CourseInfo;
//import com.example.fileprocessing.repo.AwsServiceRepository;
//import com.example.fileprocessing.service.AwsService;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.fileprocessing.repo","com.example.fileprocessing.controller","com.example.fileprocessing.config","com.example.fileprocessing.jpa.repo"})
@EntityScan(basePackages = "com.example.fileprocessing.entity")
@EnableDynamoDBRepositories(basePackages={"com.example.fileprocessing.repo","com.example.fileprocessing.config"}) 
@EnableJpaRepositories(basePackages={"com.example.fileprocessing.jpa.repo"})
public class FileprocessingApplication implements CommandLineRunner {
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	AwsServiceRepository repo;
	
	private DynamoDBMapper dynamoDBMapper;
	
	@Autowired
	AmazonDynamoDB amazonDynamoDB;
	
	@Autowired
	RelationalCourseRepository courserepo;
	

//	@Autowired
//	AwsService serv;
	public static void main(String[] args) {
		SpringApplication.run(FileprocessingApplication.class, args);
	}
	ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		FetchDyanamoDBdata.fetchAllData();
		List<CourseInfo> c=(List<CourseInfo>) repo.findAll();
		for(CourseInfo cobj:c) {
			logger.info("rrr{}",cobj.getEmployeeName());
			//RelationalCourseInfo relinfo=new RelationalCourseInfo();
			String ename=cobj.getEmployeeName();
			for(Map<String, Map<String, String>> hobj:cobj.getCourseList() ) {
				String cname="";
				Map<String, String> innerMap=null;
				for (Map.Entry<String, Map<String, String>> entry : hobj.entrySet()) {
				    cname = entry.getKey();
				    innerMap = entry.getValue();
				}
				String hours=objectMapper.writeValueAsString(innerMap);
				RelationalCourseInfo relinfo=new RelationalCourseInfo(ename,cname,hours);
				courserepo.addRecord(relinfo);
				
				
			}
			
		}

//		Optional<CourseInfo> co=repo.findById("jjjjjjj@gmail.com");
//		logger.info("{}",objectMapper.writeValueAsString(co.orElse(new CourseInfo()).getCourseList().get(0)));
//		logger.info("{}",courserepo.getAllEmployeeName());
		
//		List<CourseInfo> L=Lists.newArrayList(c);
	}

}
