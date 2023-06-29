package com.example.fileprocessing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.fileprocessing.entity.CourseInfo;
@Repository
interface AwsServiceRepository extends CrudRepository<CourseInfo, String> {

	

}

@Service
public class AwsService {
//	@Autowired
//	AwsServiceRepository rpo;
}
