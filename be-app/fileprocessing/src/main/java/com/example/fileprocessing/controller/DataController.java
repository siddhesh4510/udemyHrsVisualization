package com.example.fileprocessing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fileprocessing.entity.CourseInfo;
import com.example.fileprocessing.entity.RelationalCourseInfo;
import com.example.fileprocessing.jpa.repo.RelationalCourseRepository;
import com.example.fileprocessing.repo.AwsServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
class Course {
    private String courseName;
    private Map<String, String> courseData;

    public Course(String courseName, Map<String, String> courseData) {
        this.courseName = courseName;
        this.courseData = courseData;
    }

    public String getCourseName() {
        return courseName;
    }

    public Map<String, String> getCourseData() {
        return courseData;
    }
}

@RestController
public class DataController {
	@Autowired
	RelationalCourseRepository courserepo;
   
	ObjectMapper objectMapper=new ObjectMapper();
	
	@Autowired
	AwsServiceRepository repo;
	
	@GetMapping("/getAllEmployeeName")
	public List<String> getAllEmployeeName(){
		return courserepo.getAllEmployeeName();
	}
	@GetMapping("/getCoursesInfo")
	public List getCourses(@RequestParam("ename") String ename) {
		List<Object[]> coursesData =courserepo.getAllCourses(ename);
		List<Course> courses = new ArrayList<>();
		for (Object[] course : coursesData) {
            String courseName = (String) course[0];
            String json = (String) course[1];

        	ObjectMapper objectMapper=new ObjectMapper();
            Map<String, String> courseData=null;
			try {
				courseData = objectMapper.readValue(json, Map.class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            Course courseObj = new Course(courseName, courseData);
            courses.add(courseObj);
        }

		return courses;
	}
	
	@GetMapping("/fetchDynamoDBdata")
	public ResponseEntity<String> fetchAllData(){
		List<CourseInfo> c=(List<CourseInfo>) repo.findAll();
		for(CourseInfo cobj:c) {
//			logger.info("rrr{}",cobj.getEmployeeName());
			//RelationalCourseInfo relinfo=new RelationalCourseInfo();
			String ename=cobj.getEmployeeName();
			for(Map<String, Map<String, String>> hobj:cobj.getCourseList() ) {
				String cname="";
				Map<String, String> innerMap=null;
				for (Map.Entry<String, Map<String, String>> entry : hobj.entrySet()) {
				    cname = entry.getKey();
				    innerMap = entry.getValue();
				}
				String hours="";
				try {
					hours = objectMapper.writeValueAsString(innerMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RelationalCourseInfo relinfo=new RelationalCourseInfo(ename,cname,hours);
				List currRecords=courserepo.getCoursesEmp(ename, cname);
				if(currRecords.size()==0) {
					courserepo.addRecord(relinfo);
				}else {
				courserepo.updateRecord(relinfo);
				}
				
				
			}
			
		}
		 return ResponseEntity.ok("File downnloaded successfully");
	}

}
