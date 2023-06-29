package com.example.fileprocessing.entity;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "courseInfo")
public class CourseInfo {
    private String employeeName;
    private List<Map<String, Map<String, String>>> courseList;

    @DynamoDBHashKey(attributeName = "Employee_Name")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @DynamoDBAttribute(attributeName = "course_list")
    public List<Map<String, Map<String, String>>> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Map<String, Map<String, String>>> courseList) {
        this.courseList = courseList;
    }
}

