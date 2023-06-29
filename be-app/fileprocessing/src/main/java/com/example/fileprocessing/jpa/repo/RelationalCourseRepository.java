package com.example.fileprocessing.jpa.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.fileprocessing.entity.RelationalCourseInfo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
@Transactional
public class RelationalCourseRepository {
	@Autowired
	EntityManager em;
	
	public void addRecord(RelationalCourseInfo course) {	
		em.persist(course);
	}
	
	public void updateRecord(RelationalCourseInfo course) {	
		em.merge(course);
	}
	
	public List<String> getAllEmployeeName(){
		Query query=em.createNativeQuery("SELECT DISTINCT EMPLOYEE_NAME FROM RELATIONAL_COURSE_INFO");
		List result=query.getResultList();
		return result;
	}
	
	public List getAllCourses(String ename) {
		String sqlQuery = "SELECT COURSE_NAME,HOUR_INFO FROM RELATIONAL_COURSE_INFO WHERE EMPLOYEE_NAME = :empname";
	    Query query = em.createNativeQuery(sqlQuery);
	    query.setParameter("empname", ename);
	    List employeeCourses = query.getResultList();
	    return employeeCourses;
	}
	
	public List getCoursesEmp(String ename,String cname) {
		String sqlQuery = "SELECT COURSE_NAME,HOUR_INFO FROM RELATIONAL_COURSE_INFO WHERE EMPLOYEE_NAME = :empname AND COURSE_NAME= :cname";
	    Query query = em.createNativeQuery(sqlQuery);
	    query.setParameter("empname", ename);
	    query.setParameter("cname", cname);
	    List employeeCourses = query.getResultList();
	    return employeeCourses;
	}

}
