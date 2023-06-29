package com.example.fileprocessing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RelationalCourseInfo {
	@Id
	private String employeeName;
	@Id
	private String courseName;
	private String hourInfo;
	public RelationalCourseInfo() {
		
	}
	public RelationalCourseInfo(String employeeName,String courseName,String hourInfo) {
		this.employeeName=employeeName;
		this.courseName=courseName;
		this.hourInfo=hourInfo;
		
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getHourInfo() {
		return hourInfo;
	}
	public void setHourInfo(String hourInfo) {
		this.hourInfo = hourInfo;
	}
	

}
