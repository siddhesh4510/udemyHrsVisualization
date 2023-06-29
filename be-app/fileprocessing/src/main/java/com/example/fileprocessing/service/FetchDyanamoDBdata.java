package com.example.fileprocessing.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.fileprocessing.entity.CourseInfo;
import com.example.fileprocessing.entity.RelationalCourseInfo;
import com.example.fileprocessing.jpa.repo.RelationalCourseRepository;
import com.example.fileprocessing.repo.AwsServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FetchDyanamoDBdata {
	@Autowired
	private static
	AwsServiceRepository repo;
	@Autowired
	private static
	RelationalCourseRepository courserepo;
	
	private static ObjectMapper objectMapper=new ObjectMapper();

	
	public static void fetchAllData() throws JsonProcessingException {
		List<CourseInfo> c=(List<CourseInfo>) repo.findAll();
		for(CourseInfo cobj:c) {
			//logger.info("rrr{}",cobj.getEmployeeName());
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
	}

}
