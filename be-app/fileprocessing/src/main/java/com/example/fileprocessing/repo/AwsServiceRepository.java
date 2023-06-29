package com.example.fileprocessing.repo;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.fileprocessing.entity.CourseInfo;
//public interface AwsServiceRepository extends CrudRepository
//@Service
@EnableScan
//@Repository
public interface AwsServiceRepository extends CrudRepository<CourseInfo, String> {

	

}
